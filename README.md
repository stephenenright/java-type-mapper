The aim of this project is to simplify mapping a source type to a destination type.


# Global Configuration (Optional)
To apply global configuration that will be configured for every type mapping request, you must create a TypeMappingConfiguration instance and execute the following code once on startup 


```
	TypeMappingConfiguration configuration = TypeMappingConfiguration.create();
	// add configuration options
	TypeMappingServiceFactory.configure(configuration);

```


# Type Mapping
The project offers different mechanisms to map a type.


## Standard Type Mapping
To perform a standard type mapping from one object to another we can create a new instance of a  ``TypeMapper`` and call the map method:


```
	VendingMachineDto result = new TypeMapper().map(machine, VendingMachineDto.class);

```

The above code will convert a VendingMachine object to a VendingMachineDTO.  


### Access Level
By default only public accessible properties will be accessible.  But you can change this by setting the access level.


```
mapperConfig.setAccessLevel(TypeAccessLevel.PRIVATE);

```


### Type Mapping Includes
You can also specify includes as part of the mapping. This will mean that only the property paths that match the source object will be mapped.


```
	 mapperConfig.addIncludeMapping("*", "configuration.**", "processor.*", "slots.**" );

```

Above the ``*`` child selector indicates include in the mapping any children of a Java Bean that are not other Java Beans, Arrays, Maps, or Collections.   However, sometimes we would like to match descendant properties in this case the ``**`` descendant selector can be used.  So in the above example the `configuration.**, slots.**` paths will select any descendants of the configuration and slots properties.


### Type Mapping Excludes
We may also want to specify that some properties will be excluded.  Also note that any excluded properties will take preference over the included properties. 


```
	mapperConfig.addExcludeMapping("processor");

```

In the above code the processor property will be excluded from the mapping.  The exclude mappings currently does not support path selectors, and any descendant property of the ecluded property will also be excluded.


### Post Transformation
You can add a `TypeTransformer` to transform the destination type after the mapping is complete.

```
	Double result = new TypeMapper().setPostTransformer(( Integer source, Double dest) -> {
            return 2.0;
    }).map(100, Double.class);
```

### Property Transformation
We can also add property transformations at the property level

```
	new TypeMapper().addPropertyTransformer("name", (src, dest, currentSrc, currentDest) -> "Name Transformed")
                .map(machine, VendingMachineDto.class);
```

## Mapping To Map
The `MapMapper` is provided that offers opinionated type mapping. This type mapper will map a source object to a map, where each collection is converted to a list and any map or java bean is converted to a map.
Moreover, if the source object to be mapped is not bean, or map, and if the source object is a collection other than a map, it will be mapped to a list of elements with the key `values` in the returned map.  Also, if the source object is not a bean, collection etc it will be mapped as is with the key `value` in the returned map. 


```
   MapMapper mapper = new MapMapper();
   Map<String,Object> result = mapper.map(machine);

```

We can also map a list of elements to a list of maps

```
   MapMapper mapper = new MapMapper();
   List<Map<String,Object>> results = mapper.mapToList(machines);

```

This mapper also supports the configuration options discussed previously such as access level, includes, excludes, transformations etc.


# Installation

```
<dependency>
  <groupId>com.github.stephenenright</groupId>
  <artifactId>type-mapper</artifactId>
  <version>1.1</version>
</dependency>
```