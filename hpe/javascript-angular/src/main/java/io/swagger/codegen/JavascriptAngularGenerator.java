package io.swagger.codegen;

import io.swagger.codegen.*;
import io.swagger.models.properties.*;

import java.util.*;
import java.io.File;

public class JavascriptAngularGenerator extends DefaultCodegen implements CodegenConfig {

  protected String sourceFolder = "src";

  /**
   * Configures the type of generator.
   *
   * @return  the CodegenType for this generator
   * @see     io.swagger.codegen.CodegenType
   */
  public CodegenType getTag() {
    return CodegenType.CLIENT;
  }

  /**
   * Configures a friendly name for the generator.  This will be used by the generator
   * to select the library with the -l flag.
   *
   * @return the friendly name for the generator
   */
  public String getName() {
    return "javascript-angular";
  }

  /**
   * Returns human-friendly help for the generator.  Provide the consumer with help
   * tips, parameters here
   *
   * @return A string value for the help message
   */
  public String getHelp() {
    return "Generates a Javascript AngularJS client library.";
  }

  public JavascriptAngularGenerator() {
    super();

    supportsInheritance = false;
    setReservedWordsLowerCase(Arrays.asList("abstract",
        "continue", "for", "new", "switch", "assert", "default", "if",
        "package", "synchronized", "do", "goto", "private",
        "this", "break", "double", "implements", "protected", "throw",
        "byte", "else", "import", "public", "throws", "case", "enum",
        "instanceof", "return", "transient", "catch", "extends", "int",
        "short", "try", "char", "final", "interface", "static", "void",
        "class", "finally", "const", "super", "while"));

    languageSpecificPrimitives = new HashSet<String>(Arrays.asList(
        "string",
        "boolean",
        "number",
        "object",
        "date"));

    typeMapping = new HashMap<String, String>();
    typeMapping.put("Array", "array");
    typeMapping.put("array", "array");
    typeMapping.put("List", "array");
    typeMapping.put("boolean", "boolean");
    typeMapping.put("string", "string");
    typeMapping.put("int", "number");
    typeMapping.put("float", "number");
    typeMapping.put("number", "number");
    typeMapping.put("long", "number");
    typeMapping.put("short", "number");
    typeMapping.put("char", "string");
    typeMapping.put("double", "number");
    typeMapping.put("object", "object");
    typeMapping.put("Object", "object");
    typeMapping.put("File", "string");
    typeMapping.put("file", "string");
    typeMapping.put("integer", "number");
    typeMapping.put("Map", "object");
    typeMapping.put("map", "object");
    typeMapping.put("DateTime", "date");
    typeMapping.put("binary", "string");

    outputFolder = "generated-code/javascript-angular";
    apiTemplateFiles.put("api.mustache", ".js");
    templateDir = "javascript-angular";
  }

  @Override
  public String toApiName(String name) {
    if (name.length() == 0) {
      return "HceDefaultApi";
    }
    return "Hce" + initialCaps(name) + "Api";
  }

  /**
   * Escapes a reserved word as defined in the `reservedWords` array. Handle escaping
   * those terms here.  This logic is only called if a variable matches the reseved words
   *
   * @return the escaped term
   */
  @Override
  public String escapeReservedWord(String name) {
    return "_" + name;  // add an underscore to the name
  }

  /**
   * Location to write api files.  You can use the apiPackage() as defined when the class is
   * instantiated
   */
  @Override
  public String apiFileFolder() {
    return outputFolder + "/" + sourceFolder;
  }

  @Override
  public String toVarName(String name) {
    // replace - with _ e.g. created-at => created_at
    name = name.replaceAll("-", "_");

    // if it's all uppper case, do nothing
    if (name.matches("^[A-Z_]*$"))
      return name;

    // camelize the variable name
    // pet_id => PetId
    name = camelize(name, true);

    // for reserved word or word starting with number, append _
    if (isReservedWord(name) || name.matches("^\\d.*"))
      name = escapeReservedWord(name);

    return name;
  }

  @Override
  public String toParamName(String name) {
    // should be the same as variable name
    return toVarName(name);
  }

  @Override
  public String getSwaggerType(Property p) {
    String swaggerType = super.getSwaggerType(p);
    String type = null;
    if (typeMapping.containsKey(swaggerType)) {
      type = typeMapping.get(swaggerType);
      if (languageSpecificPrimitives.contains(type)) {
        return type;
      }
    } else {
      type = swaggerType;
    }
    return type;
  }
}
