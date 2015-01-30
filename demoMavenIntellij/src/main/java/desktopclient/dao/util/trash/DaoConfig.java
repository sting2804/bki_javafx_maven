/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package desktopclient.dao.util.trash;


import desktopclient.dao.util.trash.IConnectionFactory;

public class DaoConfig {
    private static IConnectionFactory connectionFactory = null;
    public static void init(IConnectionFactory connectionFactoryParam){
        if(connectionFactory==null){
            connectionFactory = connectionFactoryParam;
        }
    }
    
    public static IConnectionFactory getConnectionFactory(){
        return connectionFactory;
    }
}
