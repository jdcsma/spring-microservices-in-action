package jun.microservices.authentication.configuration;

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
@MapperScan(basePackages = "jun.microservices.authentication.mapper.user",
        sqlSessionFactoryRef = "UserSqlSessionFactory")
public class UserDataSourceConfiguration {

    @Bean("UserDataSource")
    @ConfigurationProperties("spring.datasource.druid.user")
    public DataSource userDataSource() {
        return new DruidDataSource();
    }

    @Bean("UserSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactoryBean(
            @Qualifier("UserDataSource") DataSource dataSource)
            throws Exception {

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] mapperLocations = resolver.getResources("classpath:mybatis/user/mapper/*.xml");
        Resource configLocation = new ClassPathResource("mybatis/user/mybatis-config.xml");

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setConfigLocation(configLocation);
        bean.setMapperLocations(mapperLocations);
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean("UserTransactionManager")
    public PlatformTransactionManager userTransactionManager(
            @Qualifier("UserDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
