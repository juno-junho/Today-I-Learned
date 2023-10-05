## springboot 3.x 버전 이상의 프로젝트에 Swagger 적용 오류 해결

-  springboot 3.x 버전 이상의 프로젝트에 Swagger 적용시 오류가 발생한다.
  - `TypeNotPresentException:Type javax.servlet.http.HttpServletRequest not present` 오류가 발생했다.
- 이유는 springboot 3.x 버전 이상부터는 Java17과 JakartaEE를 사용하는데, Swagger 제공사 SpringFox는 JakartaEE에 대한 지원을 하지 않아 빌드가 실패한다.

- 해결 방안
  1. SpringBoot 버전 downgrade
  2. 새 버전의 Swagger 출시일 까지 기다리기
  3. springdoc-openapi 사용하기

그래서 springdoc-openapi를 사용하기로 했다.
- Config 파일도 설정해 줄 필요가 없었다.
- http://localhost:8080/swagger-ui/index.html 에 자동으로 swagger 설정이 된다. 
