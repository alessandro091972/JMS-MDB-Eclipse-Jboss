package alessandro.torre.client:

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
 
import com.theopentutorials.mdb.to.Employee;
 
public class JMSClient {
 
    public static void main(String[] args) {
        TransportConfiguration transportConfiguration = 
                        new TransportConfiguration(
                NettyConnectorFactory.class.getName());  
                       
        ConnectionFactory factory = (ConnectionFactory)
            HornetQJMSClient.createConnectionFactoryWithoutHA(
                JMSFactoryType.CF,
                transportConfiguration);
         
       
        Queue queue = HornetQJMSClient.createQueue("testMyQueue");
        Connection connection;
        try {
            connection = factory.createConnection();
            Session session = connection.createSession(
                        false,
                        QueueSession.AUTO_ACKNOWLEDGE);
             
            MessageProducer producer = session.createProducer(queue);
             
           
            TextMessage message = session.createTextMessage();
            message.setText("Hello MDB!");
            producer.send(message);
            System.out.println("Sent Text Message to Queue");
             
            
            ObjectMessage objMsg = session.createObjectMessage();
             
            Employee employee = new Employee();
            employee.setId(111);
            employee.setName("Alessandro");
            employee.setDesignation("CODA_1");
            employee.setSalary(11111);
            objMsg.setObject(employee);                     
            producer.send(objMsg);
            System.out.println(" Sent Object Message to Queue.");
             
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }               
    }
}
