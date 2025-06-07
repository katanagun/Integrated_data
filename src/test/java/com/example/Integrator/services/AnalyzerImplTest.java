package com.example.Integrator.services;

import com.example.Integrator.IntegratorException;
import com.example.Integrator.interfaces.Converter;
import com.example.Integrator.interfaces.Data;
import com.example.Integrator.interfaces.Source;
import com.example.Integrator.models.DataImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnalyzerImplTest {

    private AnalyzerImpl<String> analyzer;
    private Set<String> targets;

    @BeforeEach
    void setUp() {
        targets = Set.of("характеристика", "внешность");
        analyzer = new AnalyzerImpl<>(targets);
    }

    //проверка корректности результата метода analyze при валидных данных
    @Test
    void testAnalyze_validSource_returnsAnalyzedData() throws Exception {
        // arrange
        String inputContent = "Андрей Болконский — богатый аристократ. Участвовал в сражении.";
        String expectedResponse = "характеристика: богатый аристократ\nвнешность: не указана";

        Source<String> mockSource = mock(Source.class);
        Data<String> mockData = new DataImpl<>(inputContent);
        when(mockSource.getContent()).thenReturn(mockData);

        // Подменим приватный метод sendPromptToAI через рефлексию
        Method method = AnalyzerImpl.class.getDeclaredMethod("sendPromptToAI", String.class);
        method.setAccessible(true);
        AnalyzerImpl<String> spyAnalyzer = Mockito.spy(analyzer);
        doReturn(expectedResponse).when(spyAnalyzer).sendPromptToAI(anyString());

        Data<String> result = spyAnalyzer.analyze(mockSource);

        // assert
        assertNotNull(result);
        assertTrue(result.getContent().contains("характеристика"));
    }

    // проверка на выброс исключения при пустом тексте
    @Test
    void testAnalyze_emptyContent_throwsException() {
        Source<String> mockSource = mock(Source.class);
        Data<String> mockData = new DataImpl<>("");
        try {
            when(mockSource.getContent()).thenReturn(mockData);
        } catch (IntegratorException e) {
            fail("Не должно выбрасываться исключение при моке.");
        }

        IntegratorException exception = assertThrows(IntegratorException.class,
                () -> analyzer.analyze(mockSource));

        assertEquals("Входной текст отсутствует.", exception.getMessage());
    }

    // проверяет, что analyze(Data, Converter) возвращает сконвертированный результат
    @Test
    void testAnalyzeWithConverter_returnsConvertedResult() throws Exception {
        String original = "Болконский был военным.";
        String aiOutput = "характеристика: военный";

        Data<String> inputData = new DataImpl<>(original);

        AnalyzerImpl<String> spyAnalyzer = Mockito.spy(analyzer);
        doReturn(aiOutput).when(spyAnalyzer).sendPromptToAI(anyString());

        Converter<String, Integer> stringToLength = String::length;

        Data<Integer> result = spyAnalyzer.analyze(inputData, stringToLength);

        assertEquals(aiOutput.length(), result.getContent());
    }

    // проверяет, что при null-ответе от AI выбрасывается IntegratorException
    @Test
    void testRunSemanticAnalysis_nullResponse_throwsException() throws Exception {
        AnalyzerImpl<String> spyAnalyzer = Mockito.spy(analyzer);
        doReturn(null).when(spyAnalyzer).sendPromptToAI(anyString());

        IntegratorException ex = assertThrows(IntegratorException.class,
                () -> spyAnalyzer.analyze(new DataImpl<>("Valid content")));

        assertTrue(ex.getMessage().contains("не вернул результат"));
    }
}
