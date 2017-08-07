package com.au.temp.multipane;

import com.au.temp.generate.Configuration;
import com.au.temp.utils.PsiDirectoryFinder;
import com.au.temp.utils.VirtualFileProvider;
import com.au.temp.model.*;
import com.intellij.ide.util.ClassFilter;
import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class MultipaneDialog extends DialogWrapper {

    private Project project;

    private PsiClass parentClass;

    private JPanel contentPane;
    private JTextField activityLayoutName;
    private JTextField activityClassName;
    private JCheckBox activityLauncher;
    private JTextField listItemClassName;
    private JTextField listItemLayoutName;
    private JTextField listClassName;
    private JTextField infoClassName;
    private JTextField infoLayoutName;
    private JTextField listLayoutName;
    private JTextField activityParent;
    private JComboBox<String> activityPackage;

    public MultipaneDialog(Project project) {
        super(project, true);
        init();
        setTitle("New Multipane Activity");
        System.out.println("New Multipane Activity Dialog");

        this.project = project;
        parentClass = null;

        VirtualFile java = VirtualFileProvider.getJava(project);
        if (java != null) {
            List<String> list = getPackages(java, false);
            list.sort(Comparator.naturalOrder());
            for (String p : list)
                activityPackage.addItem(p);
        } else {
            activityPackage.setEditable(true);
        }

        activityParent.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                TreeClassChooserFactory factory = TreeClassChooserFactory.getInstance(project);
                GlobalSearchScope scope = GlobalSearchScope.allScope(project);

                ClassFilter classFilter = psiClass -> {
                    for (PsiJavaCodeReferenceElement el : psiClass.getExtendsList().getReferenceElements()) {
                        if (el.getReferenceName().equals("AppCompatActivity"))
                            return true;
                    }
                    return false;
                };

                PsiClass ecClass = JavaPsiFacade.getInstance(project).findClass("AppCompatActivity", scope);
                PsiClass current = JavaPsiFacade.getInstance(project).findClass(activityParent.getText(), scope);

                TreeClassChooser chooser = factory.createInheritanceClassChooser("Choose parent class", scope, ecClass, current, classFilter);
                activityParent.transferFocus();
                chooser.showDialog();
                parentClass = chooser.getSelected();
                if (parentClass != null)
                    activityParent.setText(parentClass.getName());
            }

            @Override
            public void focusLost(FocusEvent e) {
            }
        });
    }

    public List<String> getPackages(@NotNull VirtualFile root, boolean addSelf) {
        boolean add = false;
        VirtualFile[] children = root.getChildren();
        if (children.length == 0) {
            List<String> list = new LinkedList<>();
            list.add(root.getName());
            return list;
        }
        List<String> list = new ArrayList<>();
        for (VirtualFile child : children) {
            if (child.isDirectory()) {
                System.out.println(child);
                list.addAll(getPackages(child, true));
            } else
                add = true;
        }
        if (addSelf) {
            for (int i = 0; i < list.size(); i++)
                list.set(i, root.getName() + "." + list.get(i));
            if (add)
                list.add(root.getName());
        }
        return list;
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        return super.doValidate();
    }

    public Configuration getConfiguration() {
        PsiDirectory landLayoutDirectory, portLayoutDirectory, layoutDirectory, psiPackage, utils;

        try {
            landLayoutDirectory = PsiDirectoryFinder.getLayoutLand(project);
            portLayoutDirectory = PsiDirectoryFinder.getLayoutPort(project);
            layoutDirectory = PsiDirectoryFinder.getLayout(project);
            psiPackage = PsiDirectoryFinder.getJavaPackage(project, (String) activityPackage.getSelectedItem());
            utils = PsiDirectoryFinder.getJavaUtilsPackage(project, (String) activityPackage.getSelectedItem());
        } catch (Exception e) {
            return null;
        }

        LayoutTemplateModel activityLayoutPort = new LayoutTemplateModel("MAIN_LAYOUT", portLayoutDirectory, activityLayoutName.getText()),
                activityLayoutLand = new LayoutTemplateModel("MAIN_LAYOUT", landLayoutDirectory, activityLayoutName.getText());
        activityLayoutPort.setTemplate("multipaneActivityLayoutPort.ftl");
        activityLayoutLand.setTemplate("multipaneActivityLayoutLand.ftl");
        Model activityModel = new ActivityTemplateModel("MAIN_ACTIVITY", psiPackage, activityClassName.getText(),
                activityLauncher.isBorderPaintedFlat(), parentClass, activityLayoutPort, activityLayoutLand);
        activityModel.setTemplate("MultipaneActivity");

        LayoutTemplateModel itemLayout = new LayoutTemplateModel("ITEM_LAYOUT", layoutDirectory, listItemLayoutName.getText());
        itemLayout.setTemplate("multipaneFragmentListItem.ftl");
        Model itemModel = new ItemTemplateModel("LIST_ITEM", psiPackage, listItemClassName.getText(), itemLayout);
        itemModel.setTemplate("MultipaneListItem");

        LayoutTemplateModel listFragmentLayout = new LayoutTemplateModel("LIST_LAYOUT", layoutDirectory, listLayoutName.getText());
        listFragmentLayout.setTemplate("multipaneFragmentList.ftl");
        Model listFragmentModel = new FragmentTemplateModel("LIST_FRAGMENT", psiPackage, listClassName.getText(), listFragmentLayout);
        listFragmentModel.setTemplate("MultipaneFragmentList");

        LayoutTemplateModel infoFragmentLayout = new LayoutTemplateModel("INFO_LAYOUT", layoutDirectory, infoLayoutName.getText());
        infoFragmentLayout.setTemplate("multipaneFragmentInfo.ftl");
        Model infoFragmentModel = new FragmentTemplateModel("INFO_FRAGMENT", psiPackage, infoClassName.getText(), infoFragmentLayout);
        infoFragmentModel.setTemplate("MultipaneFragmentInfo");

        Model activityAutomataModel = new NonLayoutModel("MAIN_AUTOMATA", psiPackage, "StaticActivityAutomata");
        activityAutomataModel.setTemplate("StaticMultipaneActivityAutomata");

        Model listAutomataModel = new NonLayoutModel("LIST_AUTOMATA", psiPackage, "StaticListAutomata");
        listAutomataModel.setTemplate("StaticMultipaneListAutomata");

        Model infoAutomataModel = new NonLayoutModel("INFO_AUTOMATA", psiPackage, "StaticInfoAutomata");
        infoAutomataModel.setTemplate("StaticMultipaneInfoAutomata");

        Model listItemRecyclerViewAdapter = new NonLayoutModel("RECYCLER_VIEW", psiPackage,
                listItemClassName.getText() + "RecyclerViewAdapter");
        listItemRecyclerViewAdapter.setTemplate("ListItemRecyclerViewAdapter");

        Model fragmentLifecycleModel = new NonLayoutModel("FRAGMENT_LIFECYCLE", utils, "PrimitiveFragmentLifecycle");
        fragmentLifecycleModel.setTemplate("PrimitiveFragmentLifecycle");

        return new Configuration(activityModel, itemModel, listFragmentModel, infoFragmentModel, activityAutomataModel,
                listItemRecyclerViewAdapter, listAutomataModel, infoAutomataModel, fragmentLifecycleModel);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPane;
    }
}
