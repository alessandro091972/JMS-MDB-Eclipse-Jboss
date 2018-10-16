package package alessandro.torre.mdb:

import java.util.Date;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import com.theopentutorials.mdb.to.Employee;
 
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
        propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(
        propertyName = "destination", propertyValue = "queue/MyFirstQueue") })
public class QueueMDB implements MessageListener {
    public QueueMDB() {
    }
 
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                System.out.println("Queue: received a Text Message "
                                + new Date());
                TextMessage msg = (TextMessage) message;
                System.out.println("Message is : " + msg.getText());
            } else if (message instanceof ObjectMessage) {
                System.out.println("Queue: I received an Object Message  "
                                + new Date());
                ObjectMessage msg = (ObjectMessage) message;
                Employee employee = (Employee) msg.getObject();
                System.out.println("Employee : ");
                System.out.println(employee.getId());
                System.out.println(employee.getName());
                System.out.println(employee.getDesignation());
                System.out.println(employee.getSalary());
            } else {
                System.out.println("Not a valid message");
            }
 
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
