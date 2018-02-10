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
    StringProperty Mese;

    public Alunno(String Nome, String Cognome, String Classe, String Gita, Double Importo, String Mese){
        this.Nome=new SimpleStringProperty(Nome);
        this.Cognome = new SimpleStringProperty(Cognome);
        this.Classe = new SimpleStringProperty(Classe);
        this.Gita = new SimpleStringProperty(Gita);
        this.Importo = new SimpleStringProperty(Importo.toString());
        this.Mese = new SimpleStringProperty(Mese);
    }

    public void setClasse(String classe) {
        this.Classe.set(classe);
    }

    public void setCognome(String cognome) {
        this.Cognome.set(cognome);
    }

    public void setGita(String gita) {
        this.Gita.set(gita);
    }

    public void setImporto(String importo) {
        this.Importo.set(importo);
    }

    public void setNome(String nome) {
        this.Nome.set(nome);
    }

    public void setMese(String mese) {
        this.Mese.set(mese);
    }

    public String getClasse() {
        return Classe.get();
    }

    public String getCognome() {
        return Cognome.get();
    }

    public String getGita() {
        return Gita.get();
    }

    public String getImporto() {
        return Importo.get();
    }

    public String getNome() {
        return Nome.get();
    }

    public String getMese() {
        return Mese.get();
    }
}
