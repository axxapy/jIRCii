package rero.gui.background;

import rero.config.ClientState;
import rero.config.ClientStateListener;
import rero.config.Config;

import java.awt.*;

public class BackgroundProperties implements ClientStateListener {
	protected String type;

	protected int bgType;
	protected int bgStyle;

	protected Image image;
	protected Image transform;

	protected String name;

	protected Color bgColor;
	protected float bgTint;

	protected boolean isRelative;

	public boolean isRelative() {
		return isRelative;
	}

	public int getType() {
		return bgType;
	}

	public int getStyle() {
		return bgStyle;
	}

	public Color getColor() {
		return bgColor;
	}

	public float getTint() {
		return bgTint;
	}

	public BackgroundProperties(String type, int defaultType) {
		this(type, Color.white, defaultType);
	}

	public BackgroundProperties(String type, Color defaultColor, int defaultType) {
		this(type, defaultColor, defaultType, BackgroundUtil.STYLE_TILE, .5f);
	}

	public BackgroundProperties(String _type, Color defaultColor, int defaultType, int defaultStyle, float defaultTint) {
		type = _type;

		ClientState.getInstance().addClientStateListener(type, this);
		init(defaultColor, defaultType, defaultStyle, defaultTint);
	}

	public void propertyChanged(String property, String parms) {
		init(bgColor, bgType, bgStyle, bgTint);
	}

	public void init(Color defaultColor, int defaultType, int defaultStyle, float defaultTint) {
		int _bgType, _bgStyle;
		Color _bgColor;
		float _bgTint;
		boolean _isRelative;
		String _name;

		_name = Config.getInstance().getString(type + ".image", "background.jpg");
		_bgType = Config.getInstance().getInteger(type + ".bgtype", defaultType);
		_bgColor = Config.getInstance().getColor(type + ".color", defaultColor);
		_bgTint = Config.getInstance().getFloat(type + ".tint", defaultTint);
		_bgStyle = Config.getInstance().getInteger(type + ".bgstyle", defaultStyle);
		_isRelative = Config.getInstance().isOption(type + ".relative", false);

		if (!_name.equals(name) || bgColor == null || bgType != _bgType || !bgColor.equals(_bgColor) || bgTint != _bgTint || bgStyle != _bgStyle || isRelative != _isRelative) {
			name = _name;
			bgType = _bgType;
			bgColor = _bgColor;
			bgTint = _bgTint;
			bgStyle = _bgStyle;
			isRelative = _isRelative;
			image = null;
			transform = null;
		}
	}

	public Image getImage(Component c) {
		if (image == null) {
			String imageName = Config.getInstance().getString(type + ".image", "background.jpg");
			image = BackgroundUtil.getManagedImage(c, imageName, bgTint, bgColor);
		}

		return image;
	}

	public Image getTransformedImage() {
		return transform;
	}

	public void setTransformedImage(Image i) {
		transform = i;
	}
}
