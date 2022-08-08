package com.graphics.glcanvas.engine.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TextureAtlasParser {

      public static List<TextureAtlas> load(Context context,String path) throws IOException {
          InputStream stream =context.getAssets().open(path);
          BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(stream));
          StringBuilder buffer= null;
          ArrayList<StringBuilder>atlas_text_array= new ArrayList<>();
          String line;
          while (( line=bufferedReader.readLine())!=null){
              // this must be the start of the texture atlas definition
              // csv format of a texture atlas contains the file path at the start
              if(line.contains("/")){
                  //create a new buffer
                  buffer= new StringBuilder();
                  atlas_text_array.add(buffer);
              }
             if(buffer!=null) {
                 buffer.append(line);
                 buffer.append("\n");
             }
          }

          List<TextureAtlas>atlases= new ArrayList<>();
          //create atlases
          for(StringBuilder br:atlas_text_array){
              atlases.add(new TextureAtlas(br,context));
          }
       return atlases;
      }




}
