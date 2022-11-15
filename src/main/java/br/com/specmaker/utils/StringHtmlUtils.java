package br.com.specmaker.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class StringHtmlUtils {

    public static final List<String> extractImgTagFromString(final String str){
        List<String> imgTags = new ArrayList<>(0);

        if( !Objects.isNull(str) ){
            Document doc = Jsoup.parse( str );
            Elements elements = doc.select("img[src]");

            for( Element element : elements ) {
                imgTags.add( element.attr("src") );
            }
        }

        return imgTags;
    }

}
