/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.json;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author toka
 */
@XmlRootElement
public class Order {
    @XmlElement
    public int[] product_ids;
    
    @Override
    public String toString(){
        String res = "";
        for(int i : product_ids)
            res += ","+i;
        return "["+res.substring(1)+"]";
    }
}
