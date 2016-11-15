package monty.oslapplication;

import java.util.Comparator;

import monty.oslapplication.dummy.City;

/**
 * Created by monty on 12/11/16.
 */
public class AreaComparator implements Comparator {
    @Override
    public int compare(Object o, Object t1) {

        City city_obj1 = (City) o;
        City city_obj2 = (City) t1;

        if(city_obj1.content == city_obj2.content)
        return 0;
        else if(city_obj1.content > city_obj2.content)
            return 1;
        else
            return -1;
    }
}
