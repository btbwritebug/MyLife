package com.founder.xy.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置类，获取属性值
 */
@ConfigurationProperties(prefix = "myconfig")
@Data
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyConfiguration {

    String root;

    String CoreDictionaryPath;

    String BiGramDictionaryPath;

    String CoreStopWordDictionaryPath;

    String CoreSynonymDictionaryDictionaryPath;

    String PersonDictionaryPath;

    String PersonDictionaryTrPath;

    String TraditionalChineseDictionaryPath;

    String CustomDictionaryPath;

    String CRFSegmentModelPath;

    String HMMSegmentModelPath;

    String ShowTermNature;
}
