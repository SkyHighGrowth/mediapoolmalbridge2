package MediaPoolMalBridge.clients.Bridge.jscp.client;

import MediaPoolMalBridge.persistence.entity.ReportsEntity;
import MediaPoolMalBridge.persistence.entity.enums.ReportTo;
import MediaPoolMalBridge.persistence.entity.enums.ReportType;
import MediaPoolMalBridge.persistence.repository.ReportsRepository;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class BridgeJScpClient {

    private static Logger logger = LoggerFactory.getLogger(BridgeJScpClient.class);

    @Value( "${bridge.jscp.username}")
    private String username;

    @Value( "${bridge.jscp.password}" )
    private String password;

    @Value( "${bridge.jscp.hostname}")
    private String hostname;

    @Value( "${server.ssh.public.key}")
    private String publicKey;

    @Value( "${bridge.jscp.port}" )
    private int port;

    private ReportsRepository reportsRepository;

    public BridgeJScpClient(final ReportsRepository reportsRepository)
    {
        this.reportsRepository = reportsRepository;
    }

    public void uploadFile( final String absoluteFileName )
    {
        FileInputStream fileInputStream = null;
        if( absoluteFileName == null )
        {
            return;
        }
        final String localFileName = absoluteFileName.substring( absoluteFileName.lastIndexOf( File.separator ) + 1 );
        try{
            JSch jsch = new JSch();
            jsch.setKnownHosts( new ByteArrayInputStream( (hostname  + " " + publicKey).getBytes() ) );
            Session session = jsch.getSession(username, hostname, port);
            session.setPassword(password);
            session.connect();

            boolean ptimestamp = false;

            // exec 'scp -t rfile' remotely
            String command = "scp " + (ptimestamp ? "-p" :"") +" -t "+ localFileName;
            Channel channel = session.openChannel("exec");
            ( (ChannelExec) channel).setCommand( command );

            // get I/O streams for
            //
            // remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            if( checkAck( in) != 0 ) {
                logger.error( "COMMAND {}", command );
                throw new Exception( "Can not connect to scp server " + hostname + ":" + port );
            }

            File _lfile = new File( absoluteFileName );

            if( ptimestamp ) {
                command="T " + ( _lfile.lastModified()/1000 ) + " 0";
                // The access time should be sent here,
                // but it is not accessible with JavaAPI ;-<
                command+=(" " + ( _lfile.lastModified()/1000 ) + " 0\n");
                out.write( command.getBytes() );
                out.flush();
                if( checkAck( in ) != 0 ) {
                    logger.error( "COMMAND {}", command );
                    throw new Exception( "Can not send modified for fileName " + localFileName );
                }
            }

            // send "C0644 filesize filename", where filename should not include '/'
            long filesize = _lfile.length();
            command = "C0644 " + filesize + " ";
            if( localFileName.lastIndexOf('/') > 0 ) {
                command += localFileName.substring( localFileName.lastIndexOf('/') + 1 );
            }
            else{
                command += localFileName;
            }
            command+="\n";
            out.write( command.getBytes() ); out.flush();
            if( checkAck( in ) != 0 ) {
                logger.error( "COMMAND {}", command );
                throw new Exception( "Can not send file size for fileName " + localFileName );
            }

            // send a content of lfile
            fileInputStream = new FileInputStream( _lfile );
            byte[] buf = new byte[1024];
            while( true ){
                int len = fileInputStream.read( buf, 0, buf.length );
                if( len <= 0 ) break;
                out.write(buf, 0, len); //out.flush();
            }
            fileInputStream.close();
            fileInputStream = null;
            // send '\0'
            buf[0]=0;
            out.write( buf, 0, 1 );
            out.flush();
            if( checkAck( in ) != 0 ) {
                logger.error( "COMMAND {}", command );
                throw new Exception( "Can not upload file for fileName " + localFileName );
            }
            out.close();

            channel.disconnect();
            session.disconnect();
        }
        catch( final Exception e ){
            final String message = String.format( "Can not upload file [%s] to host [%s:%s]", localFileName, hostname, port);
            final ReportsEntity reportsEntity = new ReportsEntity( ReportType.ERROR, getClass().getName(), message, ReportTo.BM, null, null,  null );
            reportsRepository.save( reportsEntity );
            logger.error( message, e );
            try{
                if( fileInputStream != null)
                    fileInputStream.close();
            }
            catch(final Exception ee) {

            }
        }
    }

    private static int checkAck(InputStream in) throws IOException {
        int b=in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if(b==0) return b;
        if(b==-1) return b;

        if(b==1 || b==2){
            StringBuffer sb=new StringBuffer();
            int c;
            do {
                c=in.read();
                sb.append((char)c);
            }
            while(c!='\n');
            if(b==1){ // error
                System.out.print(sb.toString());
            }
            if(b==2){ // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }
}
