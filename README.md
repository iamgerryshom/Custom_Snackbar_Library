# CustomSnackBar Library

**CustomSnackBar** is a customizable, enhanced Snackbar library for Android. It supports custom animations, colors, corner radii, and overlay capabilities, offering flexibility beyond the standard Snackbar.

---

## How to Use

### Add the Dependency
Add the library to your project using JitPack:
```gradle
dependencies {
    implementation 'com.github.tomleejumah:Custom_Snackbar_Library:1.3'
}
```

### Basic Example
```java
CustomSnackBar.make(context, "Title", "Description")
        .setCustomSnackBarType(CustomSnackBarType.SUCCESS)
        .show();
```

---

## Public Methods

### 1. make(Context context, String title, String description)

#### Description:
Creates a new `CustomSnackBar` instance with the specified title and description.

#### Parameters:
- `context` (Context): The context in which the Snackbar will be displayed.
- `title` (String): The title of the Snackbar.
- `description` (String): The description text of the Snackbar.

#### Example:
```java
CustomSnackBar.make(context, "Hello", "This is a Custom SnackBar!");
```

### 2. setCustomEnterAnimation(CustomAnimation customEnterAnimation)

#### Description:
Sets a custom enter animation for the Snackbar.

#### Parameters:
- `customEnterAnimation` (CustomAnimation): The animation to use when the Snackbar appears.

#### Example:
```java
setCustomEnterAnimation(CustomAnimation.POP_IN);
```

### 3. setCustomExitAnimation(CustomAnimation customExitAnimation)

#### Description:
Sets a custom exit animation for the Snackbar.

#### Parameters:
- `customExitAnimation` (CustomAnimation): The animation to use when the Snackbar disappears.

#### Example:
```java
setCustomExitAnimation(CustomAnimation.POP_OUT);
```

### 4. setCornerRadius(int cornerRadius)

#### Description:
Sets the corner radius for the Snackbar.

#### Parameters:
- `cornerRadius` (int): The radius in DP for all corners.

#### Example:
```java
setCornerRadius(12);
```

### 5. setBackgroundColor(int backgroundColor)

#### Description:
Sets the background color of the Snackbar.

#### Parameters:
- `backgroundColor` (int): The color to set.

#### Example:
```java
setBackgroundColor(Color.GREEN);
```

### 6. setStrokeWidth(int strokeWidth)

#### Description:
Sets the stroke width for the Snackbar's border.

#### Parameters:
- `strokeWidth` (int): The width of the border in DP.

#### Example:
```java
setStrokeWidth(2);
```

### 7. setStrokeColor(int strokeColor)

#### Description:
Sets the color of the Snackbar's border stroke.

#### Parameters:
- `strokeColor` (int): The color of the stroke.

#### Example:
```java
setStrokeColor(Color.RED);
```

### 8. setGravity(int gravity)

#### Description:
Sets the gravity for the Snackbar on the screen.

#### Parameters:
- `gravity` (int): The gravity constant (e.g., `Gravity.BOTTOM`).

#### Example:
```java
setGravity(Gravity.TOP);
```

### 9. setDrawOverApps(boolean drawOverApps)

#### Description:
Enables or disables drawing the Snackbar over other apps.

#### Parameters:
- `drawOverApps` (boolean): `true` to allow overlaying, `false` otherwise.

#### Example:
```java
setDrawOverApps(true);
```

### 10. setCustomSnackBarType(CustomSnackBarType customSnackBarType)

#### Description:
Sets the type of the custom Snackbar.

#### Parameters:
- `customSnackBarType` (CustomSnackBarType): The type of the Snackbar to display.

#### Example:
```java
setCustomSnackBarType(CustomSnackBarType.SUCCESS);
```

---

## Features

- **Customizable Animations:** Enter and exit animations can be personalized.
- **Appearance:** Modify corner radii, stroke colors, and background colors.
- **Overlay Capability:** Optionally overlay the Snackbar over other apps.

---

## License

This library is licensed under the [MIT License](./LICENSE).
