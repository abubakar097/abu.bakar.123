package viewdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TableView<Person> listview;
    @FXML
    private TableColumn<Person,Integer> id;
    @FXML
    private TableColumn<Person,String> name;
    @FXML
    private TableColumn<Person,String> surname;
    @FXML
    private TableColumn<Person,Integer> age;
    
   final ObservableList<Person> data = getData();
   public final ObservableList<Person> getData(){
       ObservableList<Person> list=FXCollections.observableArrayList();
       Scanner s = null;
        try {
                s=new Scanner(new File("Please enter File name"));
            while(s.hasNext()){
                String str=s.nextLine();
                Scanner sc=new Scanner(str);
                list.add(new Person(s.next(),s.next(),s.next(),s.next()));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            s.close();
        }
       return list;
   }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(new PropertyValueFactory<Person,Integer>("id")
            );
        
        name.setCellValueFactory(new PropertyValueFactory<Person,String>("name")
            );
        surname.setCellValueFactory(new PropertyValueFactory<Person,String>("surname")
            );
        age.setCellValueFactory(new PropertyValueFactory<Person,Integer>("age")
            );
        listview.setItems(data);


        
    }    
    public class Person{
        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty surname;
        private final SimpleStringProperty age;

        public Person(String id, String name, String surname, String age) {
            super();
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.surname =new SimpleStringProperty(surname);
            this.age = new SimpleStringProperty(age);
        }

        public String getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public String getSurname() {
            return surname.get();
        }

        public String getAge() {
            return age.get();
        }
    }
    
}
