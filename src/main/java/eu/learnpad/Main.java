/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package eu.learnpad;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        if (args.length != 1) {
            System.err.format("Usage: Main fileName.bpmn2");
            return;
        }

        Bpmn2ResourceFactoryImpl resourceFactory = new Bpmn2ResourceFactoryImpl();
        Resource resource = resourceFactory.createResource(URI.createFileURI(args[0]));
        resource.load(new FileInputStream(new File(args[0])), Collections.EMPTY_MAP);

        DocumentRoot dr = (DocumentRoot) resource.getContents().get(0);

        Definitions definitions = dr.getDefinitions();

        for (RootElement rootElement : definitions.getRootElements()) {
            if (rootElement instanceof org.eclipse.bpmn2.Resource) {
                org.eclipse.bpmn2.Resource bpmn2Resource = (org.eclipse.bpmn2.Resource) rootElement;
                System.out.format("Found a resource: %s\n", bpmn2Resource.getName());
            } else if (rootElement instanceof org.eclipse.bpmn2.Process) {
                org.eclipse.bpmn2.Process process = (org.eclipse.bpmn2.Process) rootElement;
                System.out.format("Found a process: %s\n", process.getName());
            }
            //...Add here other else/if clauses for other element types to be processed.
        }

        //Finally dump the read model just to show what has been read.
        resource.save(System.out, Collections.EMPTY_MAP);
    }
}
