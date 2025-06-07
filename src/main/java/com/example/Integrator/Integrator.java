package com.example.Integrator;

import com.example.Integrator.interfaces.*;
import com.example.Integrator.models.DataImpl;
import com.example.Integrator.models.SourceImpl;
import com.example.Integrator.services.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class Integrator<T> {
    public void run(String fileName) {
        System.out.println("Запуск интегратора с файлом: " + fileName);
        TaskImpl<String, String> task = new TaskImpl<>();
        Converter<String, Integer> stringToInteger = new ConverterImpl<>(Integer::parseInt);
        File configFile = new File(fileName);

        // читаем конфигурационный файл и записываем данные в map
        try{
            task.readTask(configFile);
        } catch (IntegratorException e) {
            System.err.println("Ошибка обработки задачи: " + e.getMessage());
        }

        // устанавливаем кол-во источников для preprocessor
        int lim = 0;
        try {
            lim = stringToInteger.convert(task.getProperty("source.limit"));
        } catch (IntegratorException e) {
            System.err.println("Ошибка сохранения кол-ва источников данных: " + e.getMessage());
        }

        PreprocessorImpl preprocessor = new PreprocessorImpl(lim);

        // формируем поисковый запрос
        String searchQuery = "";
        try {
            searchQuery = QueryGenerator.generateSearchQuery(task.getProperties());
            System.out.println("Сформированный поисковый запрос: " + searchQuery);

        } catch (Exception e) {
            System.err.println("Ошибка формирования поискового запроса: " + e.getMessage());
        }

        // Отправляем запрос в Яндекс
//        String searchResultHtml = "";
//        try {
//            searchResultHtml = SearchService.sendSearchRequest(searchQuery);
//        } catch (IOException e) {
//            System.err.println("Ошибка отправки поискового запроса: " + e.getMessage());
//            return;
//        }

        // Создаем исходный объект Source, где content содержит HTML-страницу
//        Document document = Jsoup.parse(searchResultHtml);
//        String pageTitle = document.title();
//        String plainTextContent = document.body().text();
//        Source<String> searchSource = new SourceImpl<>(pageTitle, new DataImpl<>(plainTextContent));

//        // Получаем список источников из preprocessor
//        try {
//            List<Source<String>> sources = preprocessor.getSources(searchSource);
//            System.out.println("Новые источники, полученные в preprocessor:");
//            for (Source<String> src : sources) {
//                System.out.println("Title: " + src.getTitle());
//                // Для демонстрации можно распечатать часть содержимого, например URL ссылки
//                System.out.println("Content: " + src.getContent().getContent());
//            }
//        } catch (IntegratorException e) {
//            System.err.println("Ошибка при получении источников: " + e.getMessage());
//        }

        String titleSource1 = "Характеристика Андрея Болконского";
        String contentSource1 = "В романе «Война и мир» Л. Н. Толстого одним из главных героев является Андрей Болконский — молодой князь, богатый и влиятельный аристократ. Всю свою короткую жизнь он находится в поиске правды, самого себя, смысла существования. Этот персонаж, наделённый богатым внутренним миром, дан в постоянном развитии. Характеристика Андрея Болконского имеет большое значение, поскольку с помощью этого образа наиболее полно раскрываются образы других героев, с которыми князь взаимодействует в романе. Кроме того, с его помощью удаётся лучше понять нравы и ценности аристократического общества, настроения, царившие в высших кругах в начале XIX столетия. Первое появление князя Андрея происходит в Петербурге, в гостиной Анны Павловны Шерер, куда он приезжает с беременной женой Лизой (I, 1, III). После званого обеда он едет в имение отца в Лысых Горах, где оставляет жену на попечении отца и младшей сестры Марьи. После чего отправляется на войну 1805 года против Наполеона в качестве адъютанта Кутузова. Участвует в Аустерлицком сражении, в котором был ранен в голову. Попадает в госпиталь французов, но возвращается на родину. По приезде домой Андрей застаёт роды жены, в которых она умирает.\n" +
                "\n" +
                "Князь Андрей винит себя в том, что был холоден с женой, не уделял ей должного внимания. После длительной депрессии Болконский влюбляется в Наташу Ростову, предлагает ей руку и сердце, однако откладывает женитьбу на год по настоянию отца и уезжает за границу. Незадолго до возвращения, князь Андрей получает от невесты письмо с отказом, причиной которого стал роман Наташи с Анатолем Курагиным. Такой поворот событий становится тяжёлым ударом для Болконского. Он мечтает вызвать Курагина на дуэль, но так и не делает этого. Чтобы заглушить боль от разочарования в любимой им женщине, князь Андрей полностью посвящает себя службе.\n" +
                "\n" +
                "Участвует в войне 1812 года против Наполеона. Во время Бородинского сражения получает осколочное ранение в живот. Среди прочих тяжелораненых Болконский видит и Анатоля, которому ампутируют ногу, и прощает его. При переезде смертельно раненный князь Андрей случайно встречает семью Ростовых, и те берут его под свою опеку. Наташа, не переставая винить себя в измене жениху, и осознавая, что всё ещё любит его, просит прощения у Андрея. Несмотря на временное улучшение состояния, князь Андрей умирает на руках Наташи и княжны Марьи. ";
        String titleSource2 = "Внешность Андрея Болконского";
        String contentSource2 = "В это время в гостиную вошло новое лицо. Новое лицо это был молодой князь Андрей Болконский, муж маленькой княгини. Князь Болконский был небольшого роста, весьма красивый молодой человек с определенными и сухими чертами. Всё в его фигуре, начиная от усталого, скучающего взгляда до тихого мерного шага, представляло самую резкую противоположность с его маленькою, оживленною женой. Ему, видимо, все бывшие в гостиной не только были знакомы, но уж надоели так, что и смотреть на них и слушать их ему было очень скучно. Из всех же прискучивших ему лиц, лицо его хорошенькой жены, казалось, больше всех ему надоело. С гримасой, портившею его красивое лицо, он отвернулся от неё.";


// Создаем источники данных
        Source<String> searchSource1 = new SourceImpl<>(titleSource1, new DataImpl<>(contentSource1));
        Source<String> searchSource2 = new SourceImpl<>(titleSource2, new DataImpl<>(contentSource2));

// Добавляем источники в препроцессор
        preprocessor.addSourceToList(searchSource1);
        preprocessor.addSourceToList(searchSource2);

// Определяем ключевые параметры анализа
        Set<String> targets = null;
        try {
            targets = Set.of(task.getProperty("targets"));
        } catch (IntegratorException e) {
            System.err.println("Ошибка сохранения кол-ва источников данных: " + e.getMessage());
        }

// Создаем экземпляр анализатора с новой логикой (использующей AI)
        AnalyzerImpl<String> analyzer = new AnalyzerImpl<>(targets);
        Reporter<String> reporter = new ReporterImpl<>();

// Получаем список источников и анализируем их
        List<Source<String>> lst = preprocessor.getListSources();

        for (Source<String> source : lst) {
            try {
                Data<String> analyzedData = analyzer.analyze(source);

                // Вывод результата анализа
                System.out.println("Анализ для источника: " + source.getTitle());
                System.out.println(analyzedData.getContent());

                reporter.add(analyzedData); // Добавляем результат в отчет
            } catch (IntegratorException e) {
                System.err.println("Ошибка анализа данных: " + e.getMessage());
            }
        }

// Вывод итогового отчета
        try {
            Data<String> report = reporter.getReport();
            System.out.println("Итоговый отчёт:\n" + report.getContent());
        } catch (IntegratorException e) {
            System.err.println("Ошибка при получении отчёта: " + e.getMessage());
        }



    }
}
