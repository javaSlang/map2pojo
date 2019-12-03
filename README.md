[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.javaslang.map2pojo/map2pojo/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.javaslang.map2pojo/map2pojo)
![](https://github.com/javaSlang/map2pojo/workflows/Java%20CI/badge.svg)
[![Maintainability](https://api.codeclimate.com/v1/badges/de8a444a25d12adfe9bb/maintainability)](https://codeclimate.com/github/javaSlang/map2pojo/maintainability)
[![Hits-of-Code](https://hitsofcode.com/github/javaSlang/map2pojo)](https://hitsofcode.com/view/github/javaSlang/map2pojo)
# map2pojo
Simple Java library for Map to POJO transformation 🧬

**How to use**.
All you need is this to get the latest version:
```xml
<dependency>
  <groupId>com.github.javaslang.map2pojo</groupId>
  <artifactId>map2pojo</artifactId>
  <version>1.0.0</version>
</dependency>
```

## Basic scenario

Transform a map to POJO:

**POJO class:**
```java
public class SimplePojo {
    private String firstName;
    private BigDecimal height;
}
```

**Transformation:**
```java
Map<String, Object> hashMap = new HashMap<String, Object>() {{
	put("FIRST_NAME", "John");
	put(" height_", "184,52");
}};
SimplePojo pojo = new Map2Pojo<>(SimplePojo.class).transform(hashMap);
```

**Result:**
```java
SimplePojo(firstName=John, height=184.52)
```

## Logic behind
Originally library supports `string`, `number` and `date` value types and conversion.

```java
Map2Pojo<TestPojoClass> testMap2Pojo = new Map2Pojo<>(
		TestPojoClass.class,
		new DefaultNormalization(),
		new DefaultFillings()
);
```
