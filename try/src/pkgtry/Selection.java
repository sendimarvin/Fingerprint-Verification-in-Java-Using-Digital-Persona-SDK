/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgtry;

import com.digitalpersona.uareu.*;
import java.util.Vector;
import javax.swing.JList;
/**
 *
 * @author link
 */
public class Selection {
    private ReaderCollection m_collection;
    private JList            m_listReaders;
    
    private Selection(ReaderCollection collection){
        m_collection = collection;
        
    }

    private void RefreshList(){
        //acquire available readers
        try{
            m_collection.GetReaders();
        }
        catch(UareUException e) {
            System.out.print("Error occured");
        }

        //list reader names
        Vector<String> strNames = new Vector<String>();
        for(int i = 0; i < m_collection.size(); i++){
            strNames.add(m_collection.get(i).GetDescription().name);
            System.out.println("Reader: " + m_collection.get(i).GetDescription().name);
        }
//        m_listReaders.setListData(strNames);
    }
    
    private Reader getSelectedReader(){
        if(-1 == m_listReaders.getSelectedIndex()) return null;
        return m_collection.get(m_listReaders.getSelectedIndex());
    }
    
    public static Reader Select(ReaderCollection collection){
    	Selection selection = new Selection(collection);
            return selection.getSelectedReader();
    }
    
    
}
