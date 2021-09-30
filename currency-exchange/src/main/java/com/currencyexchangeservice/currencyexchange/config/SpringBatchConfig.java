package com.currencyexchangeservice.currencyexchange.config;

import com.currencyexchangeservice.currencyexchange.model.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig extends DefaultBatchConfigurer {




    Logger logger = LoggerFactory.getLogger(SpringBatchConfig.class);
    @Autowired
    public DataSource dataSource;

    /*@Override
    protected JobRepository createJobRepository() throws Exception {
        MapJobRepositoryFactoryBean factoryBean = new MapJobRepositoryFactoryBean();
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }*/


    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                   ItemReader<ExchangeRate> itemReader,
                   ItemProcessor<ExchangeRate, ExchangeRate> itemProcessor,
                   ItemWriter<ExchangeRate> csvItemWriter ) {
        logger.info("Step1");
        Step step = stepBuilderFactory.get("ETL-file-load")
                .<ExchangeRate, ExchangeRate>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(csvItemWriter)
                .build();

        logger.info("Step12");
        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();

    }

    @Bean
    public FlatFileItemReader<ExchangeRate> itemReader() {

        FlatFileItemReader<ExchangeRate> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/currencyrate.csv"));
        flatFileItemReader.setName("Currency-Convert-Rate-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<ExchangeRate> lineMapper() {

        DefaultLineMapper<ExchangeRate> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","currency_from","currency_to","currency_exchange_value");

        BeanWrapperFieldSetMapper<ExchangeRate> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ExchangeRate.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

   @Bean
    public JdbcBatchItemWriter<ExchangeRate> csvItemWriter() {
        JdbcBatchItemWriter<ExchangeRate> excelAnimeWriter = new JdbcBatchItemWriter<ExchangeRate>();
        excelAnimeWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ExchangeRate>());
        excelAnimeWriter.setSql("INSERT INTO exchange_rate (id,currency_from,currency_to,currency_exchange_value) VALUES (:id, :currency_from, :currency_to, :currency_exchange_value)");
        excelAnimeWriter.setDataSource(dataSource);
        return excelAnimeWriter;
}
}
