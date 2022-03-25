package persistence

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.module.kotlin.KotlinModule
import models.Note
import java.io.File


class YAMLSerializer(private val file: File) : Serializer {
        @Throws(Exception::class)
        override fun read(): Any {
            val mapper = ObjectMapper(YAMLFactory())
            mapper.registerModule(KotlinModule())
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            return mapper.reader()
                .forType(object : TypeReference<List<Note?>?>() {})
                .readValue(File(file.toString()))}

        @Throws(Exception::class)
        override fun write(obj: Any?) {
            val mapper = ObjectMapper(YAMLFactory())
            mapper.writeValue(file, obj)
        }
    }

//https://www.w3schools.io/file/yaml-java-read-write/

