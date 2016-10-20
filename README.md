# LockPattern

## Description

Imitate Alipay gesture password

仿支付宝手势密码解锁

## Starting

创建手势密码可以查看 CreateGestureActivity.java 文件.  
登陆验证手势密码可以看 GestureLoginActivity.java 文件.

## Features

* 使用了 JakeWharton/butterknife [butterknife](https://github.com/JakeWharton/butterknife)

* 使用了 ACache 来存储手势密码

```java
/**
 * 保存手势密码
 */
private void saveChosenPattern(List<LockPatternView.Cell> cells) {
    byte[] bytes = LockPatternUtil.patternToHash(cells);
    aCache.put(Constant.GESTURE_PASSWORD, bytes);
}
```

Warning: 使用 ACache 类保存密码并不是无限期的. 具体期限可以查看 ACache 类.

* 使用了 SHA 算法保存手势密码

```java
/**
 * Generate an SHA-1 hash for the pattern. Not the most secure, but it is at
 * least a second level of protection. First level is that the file is in a
 * location only readable by the system process.
 *
 * @param pattern
 * @return the hash of the pattern in a byte array.
 */
public static byte[] patternToHash(List<LockPatternView.Cell> pattern) {
    if (pattern == null) {
        return null;
    } else {
        int size = pattern.size();
        byte[] res = new byte[size];
        for (int i = 0; i < size; i++) {
            LockPatternView.Cell cell = pattern.get(i);
            res[i] = (byte) cell.getIndex();
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            return md.digest(res);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return res;
        }
    }
}
```

* 可以开启震动模式，当选中一个圈的时候，手机会震动

```java
/**
 * Set whether the view will use tactile feedback.  If true, there will be
 * tactile feedback as the user enters the pattern.
 * @param tactileFeedbackEnabled Whether tactile feedback is enabled
 */
public void setTactileFeedbackEnabled(boolean tactileFeedbackEnabled) {
	mEnableHapticFeedback = tactileFeedbackEnabled;
}
```

* 可以开启绘制路径隐藏模式

```java
/**
 * Set whether the view is in stealth mode.  If true, there will be no
 * visible feedback as the user enters the pattern.
 * @param inStealthMode Whether in stealth mode.
 */
public void setInStealthMode(boolean inStealthMode) {
	mInStealthMode = inStealthMode;
}
```

## Example

![test.gif](https://github.com/sym900728/LockPattern/blob/master/images/test.gif)

## Contact

如果你有什么问题, 或者什么建议, 可以发邮件给我.  
Email address: symwork@163.com

## LICENSE

    Copyright 2016 Shaoyaming

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
