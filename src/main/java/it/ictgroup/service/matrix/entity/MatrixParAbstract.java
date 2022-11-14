package it.ictgroup.service.matrix.entity;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

/**
 * Classe base che rappresenta una riga della matrice di configurazione.
 *
 * @author Riccardo Merolla
 *         Created on 23/10/14.
 */
@MappedSuperclass
@SuppressWarnings("unused")
public abstract class MatrixParAbstract {

    public static final int MAX_LENGTH_VALUE = 32767;

    private KeyRowConfGlobal id;


    private String value;

    @Id
    public KeyRowConfGlobal getId()
    {
        return id;
    }

    public void setId(KeyRowConfGlobal id)
    {
        this.id = id;
    }

    @Lob
    @Column(length = MAX_LENGTH_VALUE)
    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

}