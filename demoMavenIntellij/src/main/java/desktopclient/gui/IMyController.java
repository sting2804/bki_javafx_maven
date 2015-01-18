package desktopclient.gui;

import desktopclient.entities.ISearchable;
import desktopclient.entities.Person;

/**
 * Created by sting on 17.01.15.
 */
public interface IMyController {
    void setSomeObject(ISearchable searchable);
    Person getPerson();
}
