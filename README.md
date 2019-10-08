# type-mapper


The aim of this project is to simplify mapping a source type to a destination type.


## Global Configuration (Optional)
To apply global configuration that will be configured for every type mapping request, you must create a TypeMappingConfiguration instance and execute the following code once on startup 


```
	TypeMappingConfiguration configuration = TypeMappingConfiguration.create();
	// add configuration options
	TypeMappingServiceFactory.configure(configuration);

```


## Perform Type Mapping
To perform a type mapping


```
	VendingMachineDto result = new TypeMapper().map(machine, VendingMachineDto.class);

```







