package jun.microservices.authentication.configurations;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "jun.microservices.authentication.mapper.client",
        sqlSessionFactoryRef = "ClientSqlSessionFactory")
public class ClientDataSourceConfiguration {

    @Bean("ClientDataSource")
    @ConfigurationProperties("spring.datasource.druid.client")
    public DataSource userDataSource() {
        return new DruidDataSource();
    }

    @Bean("ClientSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactoryBean(
            @Qualifier("ClientDataSource") DataSource dataSource)
            throws Exception {

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] mapperLocations = resolver.getResources("classpath:mybatis/client/mapper/*.xml");
        Resource configLocation = new ClassPathResource("mybatis/client/mybatis-config.xml");

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setConfigLocation(configLocation);
        bean.setMapperLocations(mapperLocations);
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean("ClientTransactionManager")
    public PlatformTransactionManager userTransactionManager(
            @Qualifier("ClientDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
