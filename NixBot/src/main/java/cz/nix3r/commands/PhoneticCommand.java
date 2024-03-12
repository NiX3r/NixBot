package cz.nix3r.commands;

import cz.nix3r.enums.LogType;
import cz.nix3r.utils.CommonUtils;
import cz.nix3r.utils.LogSystem;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.interaction.SlashCommandInteraction;

public class PhoneticCommand {
    public static void run(SlashCommandInteraction interaction) {

        interaction.getArguments().get(0).getStringValue().ifPresent(toCodText -> {

            char[] codeText = toCodText.toCharArray();
            String decoded = "";

            for(char c : codeText){
                decoded += " " + translateChar(c);
            }

            decoded = decoded.substring(1);

            interaction.createImmediateResponder().setContent("# Your decoded message is\n" + decoded).respond();

        });

        LogSystem.log(LogType.INFO, "End of command pause by '" + interaction.getUser().getName() + "'");

    }

    private static String translateChar(char c){

        switch (c){
            case 'A': case 'a':
                return "`Alpha``";
            case 'B': case 'b':
                return "`Bravo``";
            case 'C': case 'c':
                return "`Charlie`";
            case 'D': case 'd':
                return "`Delta`";
            case 'E': case 'e':
                return "`Echo`";
            case 'F': case 'f':
                return "`Foxtrot`";
            case 'G': case 'g':
                return "`Golf`";
            case 'H': case 'h':
                return "`Hotel`";
            case 'I': case 'i':
                return "`India`";
            case 'J': case 'j':
                return "`Juliet`";
            case 'K': case 'k':
                return "`Kilo`";
            case 'L': case 'l':
                return "`Lima`";
            case 'M': case 'm':
                return "`Mike`";
            case 'N': case 'n':
                return "`November`";
            case 'O': case 'o':
                return "`Oscar`";
            case 'P': case 'p':
                return "`Papa`";
            case 'Q': case 'q':
                return "`Quebec`";
            case 'R': case 'r':
                return "`Romeo`";
            case 'S': case 's':
                return "`Sierra`";
            case 'T': case 't':
                return "`Tango`";
            case 'U': case 'u':
                return "`Uniform`";
            case 'V': case 'v':
                return "`Victor`";
            case 'W': case 'w':
                return "`Whiskey`";
            case 'X': case 'x':
                return "`X-ray`";
            case 'Y': case 'y':
                return "`Yankee`";
            case 'Z': case 'z':
                return "`Zulu`";
            case ' ':
                return "SPACE";
            case '.':
                return "BREAK";
            default:
                return "`" + c + "`";
        }

    }

}
