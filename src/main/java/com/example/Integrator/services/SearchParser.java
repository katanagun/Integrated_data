package com.example.Integrator.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Парсер поискового ответа для извлечения ссылок.
 */
public class SearchParser {
    public static List<String> extractLinks(String html) {
        List<String> links = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements linkElements = doc.select("a[href]");

        linkElements.forEach(element -> links.add(element.attr("href")));

        return links;
    }
}
