package book.alone.config;

import org.hibernate.sql.ast.tree.from.CorrelatedTableGroup;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

@Configuration
public class RootConfig {
    @Bean
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper;
    }
}
//setFieldMatchingEnabled(true)
//기본적으로 ModelMapper는 객체의 getter와 setter 메서드를 사용하여 매핑을 수행합니다.
//이 설정을 true로 하면, 필드 이름을 기준으로 매핑을 수행할 수 있도록 활성화합니다. 즉, getter/setter가 없어도 필드 이름이 일치하면 매핑이 가능합니다.
//주로 POJO 클래스에서 getter/setter 없이 필드 매핑이 필요한 경우 유용합니다.
//setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
//필드 접근 수준을 설정합니다.
//AccessLevel.PRIVATE는 private 필드에도 접근할 수 있도록 허용합니다.
//        ModelMapper는 리플렉션(Reflection)을 사용해 private 필드에 직접 접근하여 값을 읽고 쓸 수 있습니다.
//가능한 값:
//PUBLIC: public 필드만 접근 가능
//PROTECTED: protected 및 public 필드 접근 가능
//PACKAGE_PRIVATE: 패키지 수준과 그 이상 접근 가능
//PRIVATE: 모든 필드 접근 가능 (기본적으로 가장 넓은 범위)
//setMatchingStrategy(MatchingStrategies.LOOSE)
//객체 간 매핑 전략을 설정합니다. LOOSE 전략은 필드 이름이 완전히 일치하지 않아도 유사한 이름의 필드를 매핑하려고 시도합니다.
//        예를 들어, sourceFieldName과 destinationFieldName이 약간 다르더라도 유사성을 기반으로 매핑됩니다.
//가능한 값:
//STRICT: 필드 이름과 타입이 정확히 일치해야 매핑됩니다.
//STANDARD: 기본 전략으로, 대부분의 경우 적절한 매핑을 수행합니다.
//LOOSE: 덜 엄격한 매칭을 수행하며, 이름이 부분적으로 일치하거나 유사한 경우에도 매핑됩니다.
