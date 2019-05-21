/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package refleksja.v2;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author pawel
 */
public class FXMLDocumentController implements Initializable {
    
    //obiekty fxml
    @FXML
    private Button createButton;
    @FXML 
    private Button saveButton;
    @FXML
    private VBox VBox;
    @FXML
    private TextArea logArea;
    @FXML
    private TextField textField;
    
    //pola
    public Class obj;
    Object instancja;
    Method[] methods;
    Field[] pola;
    
    
    //metody
    public boolean isClass(String className) {
    try  {
        Class.forName(className);
        return true;
        }catch (ClassNotFoundException e) {
            return false;
        }
    }   
    
    
    public String setText(Field pole) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String fieldname = "get"+pole.getName().substring(0,1).toUpperCase()+pole.getName().substring(1).toLowerCase();
        for(Method method : methods)
            {
            if(method.getName().equals(fieldname)) return method.invoke(instancja).toString();    
            }
        return "Getter not exist!!";
    }
    
    public void getText(Node child) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{

        for(Field pole : pola)
            {
                System.out.printf("2");
            if(pole.getName().equals(child.getId()))
                {
                    System.out.printf("3");
                String methodname="set"+pole.getName().substring(0,1).toUpperCase()+pole.getName().substring(1).toLowerCase();
                
                for(Method method : methods)
                    {
                        System.out.printf("4");
                    if(method.getName().equals(methodname))
                        {
                            System.out.printf("5");
                        if(child.getClass().getName().contains("TextField"))
                            {
                            TextField txt = (TextField) child;
                            if((int) method.invoke(instancja, txt.getText())==0)
                                logArea.setText(logArea.getText()+pole.getName()+": nadano wartość - "+ txt.getText()+"\n");
                                
                            else logArea.setText(logArea.getText()+pole.getName()+": BŁĘDNA WARTOŚĆ W POLU!!!\n\n");  
                            break;
                            }
                        else if(child.getClass().getName().contains("TextArea"))
                            {
                            TextArea txt = (TextArea) child;  
                            if((int) method.invoke(instancja, txt.getText())==0) 
                                logArea.setText(logArea.getText()+pole.getName()+": nadano wartość - "+ txt.getText()+"\n");
                                
                            else logArea.setText(logArea.getText()+pole.getName()+": BŁĘDNA WARTOŚĆ W POLU!!!\n\n");
                            break;
                            }
                        }
                    }
                }
            }
    }
    
    //metody fxml
    @FXML
    private void create(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        VBox.getChildren().clear();
        saveButton.setDisable(true);
        
        String Obiekt= textField.getText();
        logArea.setText("");
        
        if(isClass(Obiekt))
            {
            obj = Obiekt.getClass(); // tworzenie obiektu klasy
            Class cls = Class.forName(Obiekt);
            methods=cls.getDeclaredMethods();       //wyszukiwanie metod z podziałem na setter i getter
            
            logArea.setText("Stworzono obiekt klasy "+Obiekt+"\nWartości pól klasy:\n");
            saveButton.setDisable(false);
            
            instancja=cls.newInstance();
            pola=cls.getDeclaredFields();
            
            for(Field pole : pola)
                {
                    
                HBox hbox = new HBox();
                Label label= new Label();
                label.setText("<-- "+pole.getName());
                logArea.setText(logArea.getText()+pole.getName()+": "+setText(pole)+"\n");
                
                
                if(pole.getName().equals("tekst"))
                    {
                    TextArea ta=new TextArea();
                    ta.setId(pole.getName());
                    ta.setPromptText("Some long text");
                    ta.setText(setText(pole));
                    hbox.getChildren().add(ta);
                    }
                else
                    {
                    TextField tf=new TextField();
                    tf.setId(pole.getName());
                    tf.setPromptText("Some "+pole.getName());
                    tf.setText(setText(pole));
                    hbox.getChildren().add(tf); 
                    }
                hbox.getChildren().add(label);
                VBox.getChildren().add(hbox);
                }
            
            }
        else
            {
            logArea.setText("Klasa "+Obiekt+" nie należy do pakietu!!!");
            textField.setText("");
                
            }
        
        
        
    }
    @FXML
    private void save(ActionEvent event) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        logArea.setText("Zapisywanie zmian:\n");
        for(Node VBchild : VBox.getChildren())
            {
            HBox hbox = (HBox) VBchild;
            for(Node child : hbox.getChildren())
                {
                    System.out.printf("1");
                getText(child); 
                }
            }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        logArea.setText("Obsługiwane klasy:\n- refleksja.v2.Song");
        
    }    
    
}
