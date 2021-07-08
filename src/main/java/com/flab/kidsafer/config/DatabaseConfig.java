package com.flab.kidsafer.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;

/*
 * @Configuration :
 *  - 어노테이션 기반으로 자바 설정(빈 등록)을 할 수 있도록 돕는 어노테이션입니다.
 *  - 해당 어노테이션을 붙임으로써 이 클래스는 자바 환경설정을 하는 클래스라는 것을 명시할 수 있습니다.
 *  - 어노테이션 기반으로 환경설정을 하면, xml과 달리 type-safe를 보장할 수 있습니다.
 *  - 해당 어노테이션을 기재한 클래스에서 등록한 빈은 AnnotationConfig(Web)ApplicationContext에서 찾을 수 있습니다.
 *  - @Component 어노테이션을 사용하기 때문에, @Configuration을 등록한 클래스도 스캐닝 대상이 됩니다.
 *
 * @MapperScan :
 *  - Java 환경설정 클래스에서 MyBatis mapper 인터페이스를 빈으로 등록해주는 어노테이션입니다.
 *  - basePackages나 basePackageClasses 속성으로 매퍼를 등록할 위치를 지정할 수 있습니다.
 *  - 위 속성이 지정되어있지 않을 경우, 애노테이션을 정의한 클래스의 위치부터 스캐닝을 진행하여 빈을 등록합니다.
 */

@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan(basePackages = "com.flab.kidsafer.mapper")
public class DatabaseConfig {

    @Autowired
    private final List<TypeHandler<?>> typeHandlers;

    public DatabaseConfig(List<TypeHandler<?>> typeHandlers) {
        this.typeHandlers = typeHandlers;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    /*
     * SqlSessionFactory :
     *  - datasource의 정보를 참조하여 DB와 연결을 가능케하는 인터페이스입니다.
     *  - SqlSession을 생성 할 수 있습니다.
     *  - mapper가 위치한 경로를 추가로 셋팅할 수 있습니다.
     *  - SqlSessionTemplate Bean 등록시 SqlSessionFactory를 생성자 아규먼트에 넣어주어야 하기 때문에 빈으로 설정해주었습니다.
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        sessionFactory.setTypeHandlers(typeHandlers.toArray(new TypeHandler[typeHandlers.size()]));
        return sessionFactory.getObject();
    }

    /*
     * SqlSessionTemplate :
     *  - SqlSession을 구현하며, SqlSession의 역할을 대체합니다.
     *  - SqlSession이 스프링 트랜잭션을 사용할 수 있도록 하여, Thread-safe를 보장해줍니다.
     *  - SqlSession의 생명주기를 관리합니다.
     *  - 마이바티스의 여러 예외를 DataAccessException로 변환해줍니다.
     *  - 개념적으로 JdbcTemplate과 동일합니다.
     *
     * SqlSession :
     *  - 명령을 실행하고, mapper를 가져오고 트랜잭션을 관장하는 인터페이스입니다.
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
