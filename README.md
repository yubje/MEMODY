# Sub PJT 2

### Contents

- [Commit | Merge](#Commit  | Merge) 
- [역할분담](#역할 분담)
- [Branch 전략](# GitLab Branch 전략)
- [지라](# 지라)
- [컨벤션](# 컨벤션)
- [와이어프레임](# 와이어프레임)

### Commit  | Merge | Pull

- 시간

  - AM 10:00  | PM 16:00

  - 하루에 한번은 `develop`에 MR
  - `Front` / `Back` 에 각자 `Feature` MR 후  `develop`  Pull 땡겨서 최신화 

- Commit Message Rule

  `{JIRA issue ID} | {진행상황} | {기능 명}`

  ex) `S03P11A305-1 | Done | 회원가입`

  ex) `S03P11A305-2 | In Progress| 회원정보 수정`

  `[마무리] | {날짜 }| README.md`

  - ex) `[마무리] | 2020.07.21 | README.md`

  `[MR] | {날짜} | '{소스브랜치}' into '{타겟브랜치}'`

  
  
  **진행상황** 

  - Todo
  - In Progress
  - Done
  
  

### 역할 분담

- Front 
  - **유빈**
  - 재영
  - 유진
- Back
  - **형택**
  - 민경



### GitLab Branch 전략

- Master

  - Develop

    - Front

      - FeatureFront/doc
      - FeatureFront/에픽
        - FeatureFront/에픽/스토리(?)

    - Back

      - FeatureBack/doc
- FeatureBack/에픽
      
      

### 지라

- Epic : Front / 회원관리 (User)
  - Stroy : 로그인 (Login)



### 컨벤션

- [JavaScript](https://github.com/airbnb/javascript)

  > 자세한 내용은 공식문서 참고

  ``` javascript
  exports default {
    created() {
      }
    },
    methods: {
      setInputValue(state) {
  	  console.log('example')
      },	
    },
  }
  ```

  **Naming Convention**

  > 기본적으로 Full Name으로 씁시당

  ```javascript
  ex) res => response, err => error (keyword 걸리는건 제외입니다.)
  
  ```

  

  **Whitespace** [띄어쓰기 보세요~~]

  - Use soft tabs set to 2 spaces.

    ```javascript
    // bad
    function () {
    ∙∙∙∙var name;
    }
    
    // bad
    function () {
    ∙var name;
    }
    
    // good
    function () {
    ∙∙var name;
    }
    ```

  - Place 1 space before the leading brace.

    ```javascript
    // bad
    function test(){
      console.log('test');
    }
    
    // good
    function test() {
      console.log('test');
    }
    
    // bad
    dog.set('attr',{
      age: '1 year',
      breed: 'Bernese Mountain Dog'
    });
    
    // good
    dog.set('attr', {
      age: '1 year',
      breed: 'Bernese Mountain Dog'
    });
    ```

  - Place 1 space before the opening parenthesis in control statements (`if`, `while` etc.). Place no space before the argument list in function calls and declarations.

    ```javascript
    // bad
    if(isJedi) {
      fight ();
    }
    
    // good  띄어쓰기 보세요 ~~
    if (isJedi) {
      fight();
    }
    ```

  - Set off operators with spaces.

    ```javascript
    // bad
    var x=y+5;
    
    // good
    var x = y + 5;
    ```

  - Leave a blank line after blocks and before the next statement

    ```javascript
    // bad
    if (foo) {
      return bar;
    }
    return baz;
    
    // good
    if (foo) {
      return bar;
    }
    
    return baz;
    
    // bad
    var obj = {
      foo: function () {
      },
      bar: function () {
      }
    };
    return obj;
    
    // good
    var obj = {
      foo: function () {
      },
    
      bar: function () {
      }
    };
    
    return obj;
    ```

  **Commas**

  - Leading commas: **Nope.**

    ```javascript
    // bad
    var story = [
        once
      , upon
      , aTime
    ];
    
    // good
    var story = [
      once,
      upon,
      aTime
    ];
    ```

  **Naming Convention**

  - Use PascalCase when naming constructors or classes.

    ```javascript
    // bad
    function user(options) {
      this.name = options.name;
    }
    
    var bad = new user({
      name: 'nope'
    });
    
    // good
    function User(options) {
      this.name = options.name;
    }
    
    var good = new User({
      name: 'yup'
    });
    ```

    

  ```javascript
  // Keywords : 
  break,do,instanceof,typeof,case,else,new,var,catch,finally,return,void,continue,for,switch,while,
  debugger,function,this,with,default,if,throw,delete,in,try
  
  // Key값을 키워드로 잡지 말기
  // good
  var superman = {
    defaults: { clark: 'kent' },
    hidden: true
  };
  ```

  **Strings**

  ```javascript
  // String 쓸 때 '' 쓰기 	
  // bad
  var name = "Bob Parr";
  
  // good
  var name = 'Bob Parr';
  ```

  **Properties**

  ```javascript
  var luke = {
    jedi: true,
    age: 28
  };
  
  // bad
  var isJedi = luke['jedi'];
  
  // good
  var isJedi = luke.jedi;
  
  //Use subscript notation [] when accessing properties with a variable.
  var luke = {
    jedi: true,
    age: 28
  };
  
  function getProp(prop) {
    return luke[prop];
  }
  
  var isJedi = getProp('jedi');
  ```

  

- Use `===` and `!==` over `==` and `!=`.

  - **Objects** evaluate to **true**

  - **Undefined** evaluates to **false**

  - **Null** evaluates to **false**

  - **Booleans** evaluate to **the value of the boolean**

  - **Numbers** evaluate to **false** if **+0, -0, or NaN**, otherwise **true**

  - **Strings** evaluate to **false** if an empty string `''`, otherwise **true**

  - use shortcuts

  - ```javascript
    // bad
    if (name !== '') {
      // ...stuff...
    }
    
    // good
    if (name) {
      // ...stuff...
    }
    
    // bad
    if (collection.length > 0) {
      // ...stuff...
    }
    
    // good
    if (collection.length) {
      // ...stuff...
    }
    ```

  **if elif else**

  ```javascript
  // bad
  if (test) {
    thing1();
    thing2();
  }
  else {
    thing3();
  }
  
  // good
  if (test) {
    thing1();
    thing2();
  } else 
    thing3();
  }
  ```


#### JAVA

- [Java 컨벤션](https://myeonguni.tistory.com/m/1596)

1) 문서 주석

- 클래스 설명 주석

  - import 문 다음에 기술한다.

  - 블록주석을 사용한다.

  - 각 라인은 *로 시작한다.

  - 해당 클래스에 대한 기능과 용도 기술을 작성한다.

  - <pre>...</pre> 내용은 수정하지 않는다.

  ```java
  /* 
   * 클래스 이름
   * <pre>
   * <b> History:</b>
   *		작성자, 작성 버전, 작성 일자, (최초 작성)
   * </pre>
   *
   * @author 작성자 이름
   * @version 1.0, 수정 일자, 내용
   * @see None
   */
  ```

- 멤버 함수 주석

  - 함수의 상단에 위치한다.
  - 블록 주석을 사용한다.
  - 메소드 기능 설명은 한 두줄로 간결하게 기술한다.
  - 메소드의 파라미터를 type명과 변수명을 적고 간략하게 설명한다.

  ```java
  /**
   * 로그인 - userId와 userPw로 회원 확인 
   *
   * @param int userId - 사용자 아이디
   * @param int uwerPw - 사용자 패스워드
   * @return 로그인 성공 여부, 로그인 성공시 true return
   * @ exception 예외사항
   */
  ```

- 선언

  -  한 줄에 하나의 선언문 사용

  ```java
  int			id; 		// 사용자 아이디
  int 		pw; 		// 사용자 비밀번호
  Object 		time		// 등록 시간   
  ```

2) 들여쓰기

- 1번의 탭 또는 4번의 공백

3) 줄 나누기

- 하나의 식이 한 줄에 들어가지 않을 때

  - 콤마 후에 두줄로 나눈다.

  - 연산자 앞에서 두줄로 나눈다.

  - 들여쓰기는 8개의 빈 칸 원칙을 사용한다.

  - ```
    if ((condition1 && condition2)
    		|| (condition3 && condition4)
            ||!(condition5 && condition6)) {
        doSomethingAboutIt();
    }
    ```

4) 괄호

- 여는 괄호 다음과 닫는 괄호 이전에는 공백이 없도록 한다.

5)  명명 규칙

- Packages
  - 패키지 이름의 최상위 레벨은 소문자, 가장 높은 레벨의 도메인 이름중 하나로 설정한다.
  - 패키지 이름의 나머지 부분은 프로젝트 명으로 설정한다.
  - 해당 프로젝트에서는 com.web.blog 로 설정한다.
- Classes
  - 클래스 이름은 명사, 복합 단어일 경우 각 단어의 첫 글자는 대문자로 설정한다.
  - 첫 글자는 **대문자로 시작**한다.
- Interfaces
  - 클래스 이름과 같은 대문자 사용 규칙을 적용한다.
- Methods
  - 메소드의 이름은 동사, 복합 단어일 경우 첫 단어는 **소문자로 시작**하고, 그이후에 나오는 단어의 첫 문자는 대문자로 사용한다.
    - run();
    - runMethod();
- Variables
  - 변수 이름의 첫 번째 문자는 **소문자로 시작**하고, 각 내부 단어의 첫 번째 문자는 대문자로 시작한다.
  - 그 변수의 사용 의도를 알아낼 수 있도록 의미있게 설정한다.
- Constants
  - 클래스 상수로 선언된 변수들의 이름은 모두 **대문자로 사용**하고 각각의 단어는 언더바(_)로 분리한다.
    - static final int MIN_WIDTH = 4;



----

### Mysql 데이터베이스 규칙

1) 공통

- 소문자를 사용한다.
- snake case를 사용한다.

2) Table

- 복수형을 사용한다.
- 이름을 구성하는 각각의 단어를 underbar로 연결하여 snake case를 사용한다.
  - vip_member
- 교차 테이블의 이름은 "\_and_"로 연결한다
  - articles_and_movies

3) Column

- auto increment 속성의 PK를 대리키로 사용하는 경우, "테이블 이름의 단수형"_id 의 규칙으로 명명한다.

- 이름을 구성하는 각각의 단어를 **underscore** 로 연결하는 **snake case** 를 사용한다.

- foreign key 컬럼은 부모 테이블의 primary key 컬럼 이름을 그대로 사용한다.

  - self 참조인 경우, primary key 컬럼 이름 앞에 적절한 접두어를 사용한다.
  - 같은 primary key 컬럼을 자식 테이블에서 2번 이상 참조하는 경우, primary key 컬럼 이름 앞에 적절한 접두어를 사용한다.

- boolean 유형의 컬럼이면 "_flag" 접미어를 사용한다.

- date, datetime 유형의 컬럼이면 "_date" 접미어를 사용한다.

  ```mysql
  article_id, movie_id : "테이블 이름의 단수형" + "_id"
  complete_flag : boolean 유형의 컬럼
  issue_date : 날짜 유형의 컬럼
  ```

4) Foreign Key

- 이름을 구성하는 각각의 단어를 **hyphen** 으로 연결하는 **snake case** 를 사용한다.

- "fk"-"부모 테이블 이름"-"자식 테이블 이름"

  - 같은 부모-자식 테이블에 2개 이상의 foreign key가 있는 경우, numbering합니다.

    ```mysql
    fk-movies-articles : article 테이블이 movie 테이블을 참조
    fk-admins-notices-1 / fk-admins-notices-2 : notices 테이블이 admins 테이블을 2회 이상 참조하여 numbering
    ```

    

5) Index

- 이름을 구성하는 각각의 단어를 **hyphen** 으로 연결하는 **snake case** 를 사용한다.
- 접두어
  - unique index : uix
  - spatial index : six
  - index : nix
- "접두어"-"테이블 이름"-"컬럼 이름"-"컬럼 이름"

```mysql
uix-accounts-login_email
```



### 와이어프레임

- 발사믹

