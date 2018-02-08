package sample;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Alunno extends RecursiveTreeObject<Alunno> {
    StringProperty Nome;
    StringProperty Cognome;
    StringProperty Classe;
    StringProperty Gita;
    StringProperty Importo;

    public Alunno(String Nome, String Cognome, String Classe, String Gita, String Importo){
        this.Nome=new SimpleStringProperty(Nome);
        this.Cognome = new SimpleStringProperty(Cognome);
        this.Classe = new SimpleStringProperty(Classe);
        this.Gita = new SimpleStringProperty(Gita);
        this.Importo = new SimpleStringProperty(Importo);
    }
}
