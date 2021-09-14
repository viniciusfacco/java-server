package server;

/**
 *
 * @author vinicius.rodrigues
 */

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import javax.naming.*;

public class server {

    public static void main(String[] args) {
        
        Context jndi = null;
        ConnectionFactory connfact = null;
        Connection conn = null;
        Session sess = null;
        Topic tp = null;
        TextMessage msg = null;
        MessageProducer msgprd = null;
        
        Scanner commandLine = new Scanner(System.in);
    
        System.out.println("iniciando");
        
        try {
            jndi = new InitialContext();
        } catch (NamingException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("instanciando connectionfactory");
        try {
            connfact = (ConnectionFactory) jndi.lookup("TopicConnectionFactory");
        } catch (NamingException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("instanciando connection");
        try {
            conn = connfact.createConnection();
        } catch (JMSException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("instanciando session");
        try {
            sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("conectando topic");
        try {
            tp = (Topic) jndi.lookup("vini");
        } catch (NamingException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("criando messageproducer");
        try {
            msgprd = sess.createProducer(tp);
        } catch (JMSException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        System.out.println("criando mensagem");
        try {
            msg = sess.createTextMessage();
        } catch (JMSException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        System.out.println("Digite a mensagem");
        String s = commandLine.nextLine();
        
        while (!s.equalsIgnoreCase("exit")){
            System.out.println("setando mensagem");
            try {
                msg.setText(s);
            } catch (JMSException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("enviando mensagem");
            try {
                msgprd.send(msg);
            } catch (JMSException ex) {
                Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Digite a mensagem");
            s = commandLine.nextLine();
        }
        System.out.println("setando mensagem");
        try {
            msg.setText(s);
        } catch (JMSException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("enviando mensagem");
        try {
            msgprd.send(msg);
        } catch (JMSException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
}
