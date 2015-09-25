package de.uniulm.omi.cloudiator.lance.lca.container.environment;

import de.uniulm.omi.cloudiator.lance.lca.container.port.DownstreamAddress;
import de.uniulm.omi.cloudiator.lance.lca.container.port.NetworkVisitor;
import de.uniulm.omi.cloudiator.lance.lca.container.port.PortHierarchyLevel;
import de.uniulm.omi.cloudiator.lance.lca.containers.plain.shell.PlainShell;
import de.uniulm.omi.cloudiator.lance.lifecycle.ExecutionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Daniel Seybold on 10.09.2015.
 */
public class PowershellExportBasedVisitor implements NetworkVisitor, PropertyVisitor{

    private static final Logger LOGGER = LoggerFactory.getLogger(PowershellExportBasedVisitor.class);

    private final ShellLikeInterface shellLikeInterface;

    public PowershellExportBasedVisitor(PlainShell plainShell) {
        shellLikeInterface = plainShell;

    }

    public void addEnvironmentVariable(String name, String value) {
        //fixme: replace all doubles quotes to single quotes to be able to execute the powershell command;
        //do not use double quotes for powershell
        //ExecutionResult result = shellLikeInterface.executeCommand("[Environment]::SetEnvironmentVariable(\"" + name + "\", \"" + value + " \", \"User\")");
        ExecutionResult result = shellLikeInterface.executeCommand("[Environment]::SetEnvironmentVariable('" + name + "', '" + value + " ', 'User')");
        if(!result.isSuccess()) {
            //shellLikeInterface.executeCommand("[Environment]::SetEnvironmentVariable(\"" + name + "\", \"" + value + " \", \"User\")");
            throw new IllegalStateException("could not set environment variables: " + name + "=" + value + "\n output: " + result.getOutput() );

        }
        LOGGER.debug("Successfull set env var: " + name + " = " + value);

    }


    @Override
    public void visitNetworkAddress(PortHierarchyLevel level, String address) {
        addEnvironmentVariable(level.getName().toUpperCase() + "_IP", address);
    }

    @Override
    public void visitInPort(String portName, PortHierarchyLevel level, Integer portNr) {
        addEnvironmentVariable(level.getName().toUpperCase() + "_" + portName.toUpperCase(), portNr.toString());
    }

    @Override
    public void visitOutPort(String portName, PortHierarchyLevel level, List<DownstreamAddress> sinks) {
        String value = "";
        for(DownstreamAddress element : sinks) {
            if(!value.isEmpty()) {
                value = value + ",";
            }
            value = value + element.toString();
        }
        addEnvironmentVariable(level.getName().toUpperCase() + "_" + portName, value);
    }

    @Override
    public void visit(String propertyName, String propertyValue) {
        addEnvironmentVariable(propertyName, propertyValue);
    }
}
