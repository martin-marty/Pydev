package org.python.pydev.poetry;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class PoetryPlugin extends Plugin {

	private static PoetryPlugin plugin;

	/**
	 * The constructor.
	 */
	public PoetryPlugin() {
		plugin = this;
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static PoetryPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative
	 * path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
//	public static ImageDescriptor getImageDescriptor(String path) {
//		return AbstractUIPlugin.imageDescriptorFromPlugin("org.python.pydev.django", path);
//	}

	public static String getPluginID() {
		return getDefault().getBundle().getSymbolicName();
	}

}
