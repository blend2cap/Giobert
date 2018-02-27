package sample.Repositories;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class NameSurname extends RecursiveTreeObject<NameSurname> {
    public StringProperty name;
    public StringProperty surname;

    public NameSurname(String name, String surname){
        this.name=new SimpleStringProperty(name);
        this.surname=new SimpleStringProperty(surname);
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getSurname() {
        return surname;
    }

    public String getSurnameValue(){
        return String.valueOf(surname);
    }
    public void setName(StringProperty name) {
        this.name = name;
    }

    public void setSurname(StringProperty surname) {
        this.surname = surname;
    }
}
