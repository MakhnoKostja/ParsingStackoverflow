package ua.kmakhno.iam.pr;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by I am on 29.05.2016.
 */
public class HtmlHelper {
    TagNode rootNode;

    public HtmlHelper(URL htmlPage) throws IOException{

        //Создаём объект HtmlCleaner
        HtmlCleaner cleaner = new HtmlCleaner();
        //Загружаем html код сайта
        rootNode = cleaner.clean(htmlPage);
    }

    List<TagNode> getLinksByClass(String cssClassName){
            List<TagNode> linkList = new ArrayList<>();

        //Выбираем все ссылки
        TagNode linkElements[] = rootNode.getElementsByName("a", true);
        for(int i = 0; linkElements != null && i < linkElements.length; i++){
        //получаем  атрибут по имени
            String classType = linkElements[i].getAttributeByName("class");
            if(classType != null && classType.equals(cssClassName)){
                linkList.add(linkElements[i]);
            }
        }
        return linkList;
    }
}
