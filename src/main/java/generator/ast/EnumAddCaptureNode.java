/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator.ast;

/**
 *  Enum pro přehlednější ASTBuilder
 * @author Jan Lejnar
 */
public enum EnumAddCaptureNode {
    TRUE(true), 
    FALSE(false);
    
    private EnumAddCaptureNode(boolean value) {
        this.value = value;
    }
    
    public final boolean value;
}
