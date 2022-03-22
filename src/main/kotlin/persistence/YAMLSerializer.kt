package persistence

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import models.Note
import java.io.File


class YAMLSerializer(private val file: File) : Serializer {
        @Throws(Exception::class)
        override fun read(): Any {
            val objectMapper = ObjectMapper(YAMLFactory())
            val obj = objectMapper.readValue(file,Object::class.java)

            return obj
        }

        @Throws(Exception::class)
        override fun write(obj: Any?) {
            val objectMapper = ObjectMapper(YAMLFactory())
            objectMapper.writeValue(file, obj as ArrayList<Note>)
        }
    }

//https://www.w3schools.io/file/yaml-java-read-write/

