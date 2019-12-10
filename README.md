[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.javaslang.map2pojo/map2pojo/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.javaslang.map2pojo/map2pojo)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](https://opensource.org/licenses/MIT)

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
	put(" height_", "184.52");
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
`TestPojoClass.class` ‒ target POJO class

`new DefaultNormalization()` ‒ basic implementation of `Function<String, String>` for keys normalization providing the correlation between keys in the map and field names in the POJO.

`new DefaultFillings()` ‒ basic implementation of `Fillings` interface providing conversion of corresponding values from the original map to the POJO.

## Annotations
`@Map2Pojo.OrderedFields`: provides the possibility for transformation basing on the order of keys / fields.

Retention: `RUNTIME`.

Target: `TYPE`.

**Example:**

POJO:
```java
@Map2Pojo.OrderedFields
public static class OrderedTestPojo {
    private String firstField;
    private Date secondField;
    private BigDecimal thirdField;
}
```

Original map:
```java
new HashMap<String, Object>() {{
    put("0", ...);
    put("1", ...);
    put("2", ...);
}};
```
___
`@Map2Pojo.FormattedDate`: provides the possibility of `from` / `to` date conversion basing on the specified `format` value.

Retention: `RUNTIME`.

Target: `FIELD`.

**Example:**

POJO:
```java
...
@Map2Pojo.FormattedDate("dd-MM-yyyy")
private Date annotatedTestDate;
@Map2Pojo.FormattedDate("dd-MM-yyyy")
private String dateAsString;
...
```

Original map:
```java
new HashMap<String, Object>() {{
    ...
    put("Annotated Test Date", ...);
    put("DATE_AS_STRING", ...);
    ...
}};
```
___
`@Map2Pojo.Locale`: provides the possibility of `number` value conversion basing on the `locale`.

Retention: `RUNTIME`.

Target: `TYPE`, `FIELD`.

**Example:**

POJO:
```java
...
@Map2Pojo.Locale("de")
private BigDecimal rate;
...
```
```java
@Map2Pojo.Locale("de")
public static class TestPojo {
    ...
}
```

Original map:
```java
new HashMap<String, Object>() {{
    ...
    put("RATE", "100,000000");
    ...
}});
```