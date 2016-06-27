## "which jackson" to use
"The package names in Jackson 2.x got changed to com.fasterxml from org.codehaus."



https://github.com/FasterXML/jackson
Streaming (docs) ("jackson-core") defines low-level streaming API, and includes JSON-specific implementations
Annotations (docs) ("jackson-annotations") contains standard Jackson annotations
Databind (docs) ("jackson-databind") implements data-binding (and object serialization) support on streaming package; it depends both on streaming and annotations packages



https://github.com/FasterXML/jackson-core
Core jar is a functional OSGi bundle, with proper import/export declarations.

https://github.com/FasterXML/jackson-annotations

https://github.com/FasterXML/jackson-databind



[1]: http://stackoverflow.com/questions/18429468/correct-set-of-dependencies-for-using-jackson-mapper "which to use"