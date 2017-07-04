package simpleWebSvcAuth.models;

import java.util.ArrayList;
import java.util.List;

public class Pager
{
    private static final int pageSize = 3;

    public static <T> List<T> getPage(Iterable<T> objects, int pageNumber)
    {
        List<T> result = new ArrayList<T>();
        int startIndex = pageNumber*pageSize;
        int endIndex = startIndex+pageSize;
        int index = 0;

        for (T object:objects)
        {
            if (index >= startIndex)
                result.add(object);

            index++;

            if (index == endIndex)
                break;
        }

        return result;
    }
}
