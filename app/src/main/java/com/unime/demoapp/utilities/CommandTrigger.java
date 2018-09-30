package com.unime.demoapp.utilities;

import android.content.Context;
import android.util.Log;

import com.unime.ctrlbeacon.Config;
import com.unime.ctrlbeacon.CtrlBeacon;
import com.unime.ctrlbeacon.objectinteraction.BeaconCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 *
 */

public class CommandTrigger {
    private List<SmartObject> smartObjectList;
    private String name;
    private String command;
    private Context context;

    private Config mConfig;


    public CommandTrigger(String name, String command, Context context) {
        smartObjectList = new ArrayList<>();
        this.name = name;
        this.command = command;
        this.context = context;
        mConfig = Config.getInstance(context.getApplicationContext());
    }

    public String getCommand() {
        return command;
    }

    public String getName() {
        return name;
    }

    public Context getContext() {
        return context;
    }

    public List<SmartObject> getSmartObjectList() {
        return smartObjectList;
    }

    private void fill() {
        SmartObject lampObject = new SmartObject("lamp");
        SmartObject doorObject = new SmartObject("door");

        lampObject.setCommands(Arrays.asList("turn on", "turn off"));
        doorObject.setCommands(Arrays.asList("open"));

        smartObjectList.add(lampObject);
        smartObjectList.add(doorObject);
    }

    public void tryCommand() {
        fill(); // TODO remove this fill method and fill the List from the file
        if(isValid(getName(), getCommand())) {
            startCommand(getName(), getCommand());
        }
    }

    private boolean isValid(String name, String command) {
        boolean status = false;


        // check if the command is contained in the commands associated with the object recognized
        for (SmartObject smartObject : getSmartObjectList()) {
            if (name.equals(smartObject.getName())) {
                if (smartObject.getCommands().contains(command)) {
                    status = true;
                }
            }
        }

        Log.d(TAG, "isValid: " + status + " " + name + " " + command);

        return status;
    }

    private void startCommand(String name, String command) {
        BeaconCommand beaconCommand = vocalToBeaconCommand(command);

        CtrlBeacon.sendCommand(beaconCommand, context);
    }

    private BeaconCommand vocalToBeaconCommand(String command) {
        BeaconCommand beaconCommand = new BeaconCommand();
        beaconCommand.setCounter(mConfig.getCounter());

        // TODO just for a prototype
        if(command.equals("turn on")) {
            Log.d(TAG, "vocalToBeaconCommand: turn on");
            // beaconCommand.setBitmap((byte)0b11111111); // it works!


            beaconCommand.setCommandType("01");
            beaconCommand.setCommandClass("00");
            beaconCommand.setCommandOpCode("01"); // todo this is 04 in the demo
            beaconCommand.setParameters("00", "00");

        } else if (command.equals("turn off")) {
            Log.d(TAG, "vocalToBeaconCommand: turn off");

            beaconCommand.setCommandType("01");
            beaconCommand.setCommandClass("00");
            beaconCommand.setCommandOpCode("05");
            beaconCommand.setParameters("00", "00");
        } else {
            return null;
        }

        beaconCommand.setUserId(mConfig.getUserId());
        beaconCommand.setObjectId("0000"); //TODO read this value from json

        return beaconCommand;
    }
}
