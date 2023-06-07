# 6월 7일 - 프레임워크를 위한 JAVA (4)

# Collection 이야기

## Collection

- **여러 데이터의 묶음**을 컬렉션이라고 합니다.
- 컬렉션은 추상체 입니다.
- Collection (추상제)
    - List
        - LinkedList (구상체)
        - ArrayList
        - Vector
        - Stack
    - Set
        - HashSet

→ **data를 묶음 단위로 처리한다.**

## Iterator

- 여러 데이터의 묶음을 풀어서 **하나씩 처리할 수 있는 수단을 제공**합니다.
- `next()` 를 통해서 다음 데이터를 조회 할 수 있습니다.
- 역으로 움직일 수 없습니다. (이전 데이터를 조회할 수는 없습니다.)

## Stream

- Java 8 이상에서 부터 사용 가능합니다.
- **데이터의 연속입니다.**
- iter는 끝이 어딘지 알 수 없다. next만 알 수 있다.
- stream도 연속되는 데이터 하나만 취급한다.
- (System.in / System.out →  이것도 스트림 입니다. 이미 우리가 쓰고 있던 겁니다.)
- Collections.stream()을 제공해 줍니다. (Java 8)
- filter, map, forEach 같은 고차함수 (함수를 인자로 받는 함수)가 제공됩니다.

**[Collection과 Stream 차이점]**

MyCollection은 map을 다 수행하고 난 데이터 덩어리가 나와서 데이터 덩어리를 MyCollection에 전달해서 한꺼번에 결과가 만들어짐.

Stream은 데이터 한건 한건 떨어뜨려 처리함. 필요 없으면 처리 하지 않고 필요한 시점 까지 뒤로 미뤘다가 처리하는 lazy evaluation 처리가 구현 되어 있어 효율적으로 구현함.

동작들이 multi-thread로 동작하거나, parallel하게 동작하면서 좋은 퍼포면스 결과를 보여주기도 한다.

- Stream을 만들 때는 `Stream.generate()`와 `Stream.iterate()`으로 만들 수 있습니다.
- Stream을 사용하면 연속된 데이터에 대해서 **풍부한 고차함수들을 사용하여 강력한 기능을 간결하게 표현할 수 있습니다.**

## Optional

- NPE : NullPointerException :→ 가장 많이 발생하는 에러 중의 하나.
- 자바에서는 (거의) 모든 것이 레퍼런스 ⇒ (거의) 모든 것이 null이 될 수 있다.
- **항상 null을 확인할 필요가 있습니다.**
- **이제부터 null을 쓰지마!** ⇒ 서로 약속! : 계약한다 : 계약을 하고 프로그래밍 한다. (요즘 개발 트랜드)

1. EMPTY 객체를 사용하는 방법
2. `Optional`을 사용한다.
- Optional
    - null 데이터 : `Optional.empty()` 사용한다.
    - 데이터 : `Optional.of(DATA)` 사용한다.

```java
        if (optionalUser.isPresent()) {
            // do something 1...
        } else {
            // do something 2...
        }

        optionalUser.ifPresent(user -> {
            // do something 1...
        });
        optionalUser.ifPresentOrElse(user -> {
            // do something 1...
        }, () -> {
            // do something 2...
        });
```

→ data가 없을때나 있을때나 Optional에 감싸서 보내줄 테니, 값을 있을때만 처리할 수 있는 기능을 강제할 수 있다.

- EMPTY 객체는 만들고 사용하겠다 약속한 사람들끼리만 아는 것. → Context가 없으면 사용할 줄 모른다.
- 하지만 Optional은 값이 있을수도, 없을 수도 있다는 context 포함된 정보를 제공해주기 때문에 어떻게 취급해야 하는지 명확하게 알 수 있다.