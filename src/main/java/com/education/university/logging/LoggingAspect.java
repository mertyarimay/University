package com.education.university.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect   //aspect kullanıldığı belirtmek için kullanıyoruz
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class); //logger nesnesini oluştuma

    @Around("execution(* com.education.university.business.service.*.*(..))") //@around metodun hem öncesi hem sonrasında çalışır
    //business  paketindeki tüm metotları hedef alır yani hepsi için konsola log u yazılır
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable { //around kullanıldığı için metodun sonucunu döndürmek zorundatız bu yüzde object dönüyor after kullansak void dönebiliriz
        String methodName = joinPoint.getSignature().getName();  //çağrılan metodun adını alır
        logger.info("Method çağrıldı: {}", methodName);// logger.ınfo metodun çağrıldığını gösterir metot öncesi durumu

        Object result;
        try {
            result = joinPoint.proceed(); // Metodu çalıştır  metot başarılı bir şekilde çalışırsa sonuç resulta atanır metot öncesi durumu
            if(result==null){
                logger.warn("Method {} boş değer döndürdü",methodName);
            } else if (result instanceof Boolean) {  //result değişkeni boolean olup olmadığını kontrol eder
                if ((Boolean) result){  //sonuç boolean mu diye kontrol edilir
                    logger.info("Silme işlemi gerçekleşti: {}",methodName);
                }else {
                    logger.warn("Başarılı ama silme işlemi gerçekleşmedi: Method {}, result = {}",methodName,result);
                }

            } else {
                logger.info("Başarılı: Method {}", methodName);  //log mesajı ile başarılı çalıştığı gözükür metot sonrası durumu

            }
        } catch (Throwable throwable) {   //eğer bir hata oluşursada hata fırlatır
            logger.error("BAŞARISIZ: Method {}, hata mesajı: {}", methodName, throwable.getMessage());
            throw throwable; // Hata fırlat
        }

        return result;
    }




    //After örneği

   /* public class LoggingAspect {

        private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

        @After("execution(* com.education.university.business.service.*.*(..))")
        public void logAfter(JoinPoint joinPoint) {
            String methodName = joinPoint.getSignature().getName();
            logger.info("Başarılı", methodName);
        }*/

 //Before örneği

   /* public class LoggingAspect {

        private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

        @Before("execution(* com.education.university.business.service.*.*(..))")
        public void logBefore(JoinPoint joinPoint) {
            String methodName = joinPoint.getSignature().getName();
            logger.info("Method Çağrıldı", methodName);
        }
    }  */


    //tüm metotlara değil sadece get all için
   /* public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.education.university.business.*.getAll(..))")
    public Object logAroundGetAll(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Method Çağrıldı", methodName);

        Object result;
        try {
            result = joinPoint.proceed(); // Metodu çalıştır
            logger.info("Başarılı", methodName);
        } catch (Throwable throwable) {
            logger.error("BAŞARISIZ", methodName, throwable.getMessage());
            throw throwable;
        }

        return result;
    }*/
}

