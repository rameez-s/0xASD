package engine.rendering;

import engine.math.Matrix4f;
import engine.math.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.lwjgl.opengl.GL20.*;

/**
 * Created by 18iwahlqvist on 2/12/2017.
 */
public class Shader {
    public int program, vertShader, fragShader;

    public Shader(String filename){
        program = glCreateProgram();

        vertShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertShader, readFile(filename + ".vertShader"));
        glCompileShader(vertShader);
        if(glGetShaderi(vertShader, GL_COMPILE_STATUS) != 1){
            System.out.println("Something wrong with the vertex shader");
            System.out.println(glGetShaderInfoLog(vertShader));
            System.exit(1);
        }
        fragShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragShader, readFile(filename + ".fragShader"));
        glCompileShader(fragShader);
        if(glGetShaderi(fragShader, GL_COMPILE_STATUS) != 1){
            System.out.println("Something wrong with the fragment shader");
            System.out.println(glGetShaderInfoLog(fragShader));
            System.exit(1);
        }

        glAttachShader(program, vertShader);
        glAttachShader(program, fragShader);

        glBindAttribLocation(program, 0, "vertices");
        glBindAttribLocation(program, 1, "texture");

        glLinkProgram(program);
        if(glGetProgrami(program, GL_LINK_STATUS) != 1){
            System.out.println("Something wrong with the program linking");
            System.out.println(glGetProgramInfoLog(program));
            System.exit(1);
        }
        glValidateProgram(program);
        if(glGetProgrami(program, GL_VALIDATE_STATUS) != 1){
            System.out.println("Something wrong with the program validation");
            System.out.println(glGetProgramInfoLog(program));
            System.exit(1);
        }

        glDeleteShader(fragShader);
        glDeleteShader(vertShader);
    }

    public void setUniform(String name, int value){
        int location = glGetUniformLocation(program, name);
        if(location != -1){
            glUniform1i(location, value);
        }
    }

    public void setUniform(String name, Matrix4f value){
        int location = glGetUniformLocation(program, name);
        if(location != -1){
            glUniformMatrix4fv(location, false, value.toFloatBuffer());
        }
    }
    public void setUniform(String name, Vector3f value){
        int location = glGetUniformLocation(program, name);
        if(location != -1){
            glUniform3f(location, value.x, value.y, value.z);
        }
    }

    public void bind(){
        glUseProgram(program);
    }

    private String readFile(String filename){
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(new File("shaders/" + filename)));
            String line;
            while((line = br.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
