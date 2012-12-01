/*
 * (C) Copyright 2012 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Antoine Taillefer <ataillefer@nuxeo.com>
 */
package org.nuxeo.drive.adapter.impl;

import java.util.ArrayList;
import java.util.List;

import org.nuxeo.drive.adapter.AbstractDocumentBackedFileSystemItem;
import org.nuxeo.drive.adapter.FileSystemItem;
import org.nuxeo.drive.adapter.FolderItem;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.DocumentRef;

/**
 * {@link DocumentModel} backed implementation of a {@link FolderItem}.
 *
 * @author Antoine Taillefer
 */
public class DocumentBackedFolderItem extends
        AbstractDocumentBackedFileSystemItem implements FolderItem {

    public DocumentBackedFolderItem(DocumentModel doc) {
        super(doc);
    }

    /*--------------------- AbstractDocumentBackedFileSystemItem ---------------------*/
    @Override
    public String getName() throws ClientException {
        return getDocument().getTitle();
    }

    @Override
    public boolean isFolder() {
        return true;
    }

    /*--------------------- FolderItem -----------------*/
    @Override
    public List<FileSystemItem> getChildren() throws ClientException {

        // TODO: use a pageProvider
        String parentId = doc.getId();
        DocumentModelList dmChildren = getCoreSession().query(
                String.format(
                        "select * from Document where ecm:parentId='%s' order by dc:created asc",
                        parentId));

        List<FileSystemItem> children = new ArrayList<FileSystemItem>(
                dmChildren.size());
        for (DocumentModel dmChild : dmChildren) {
            FileSystemItem child = dmChild.getAdapter(FileSystemItem.class);
            if (child != null) {
                children.add(child);
            }
        }
        return children;
    }

    @Override
    public void addChild(FileSystemItem child) {
        // TODO
    }

}
