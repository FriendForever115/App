import React, {memo, useCallback, useContext} from 'react';
import {Gesture, GestureDetector} from 'react-native-gesture-handler';
import Animated, {useSharedValue} from 'react-native-reanimated';
import {View, StyleSheet} from 'react-native';
import styles from '../../../../styles/styles';
import {attachmentViewPdfPropTypes, attachmentViewPdfDefaultProps} from './propTypes';
import AttachmentCarouselPagerContext from '../../AttachmentCarousel/Pager/AttachmentCarouselPagerContext';
import BaseAttachmentViewPdf from './BaseAttachmentViewPdf';


function AttachmentViewPdf(props) {
    const attachmentCarouselPagerContext = useContext(AttachmentCarouselPagerContext);
    const scaleRef = useSharedValue(1);
    const offsetX = useSharedValue(0);
    const offsetY = useSharedValue(0);

    const Pan = Gesture.Pan()
        .manualActivation(true)
        .onTouchesMove((evt) => {
            if (offsetX.value !== 0 && offsetY.value !== 0) {
                // if the value of X is greater than Y and the pdf is not zoomed in,
                // enable  the pager scroll so that the user
                // can swipe to the next attachment otherwise disable it.
                if (Math.abs(evt.allTouches[0].absoluteX - offsetX.value) > Math.abs(evt.allTouches[0].absoluteY - offsetY.value) && scaleRef.value === 1) {
                    attachmentCarouselPagerContext.shouldPagerScroll.value = true;
                } else {
                    attachmentCarouselPagerContext.shouldPagerScroll.value = false;
                }
            }
            offsetX.value = evt.allTouches[0].absoluteX;
            offsetY.value = evt.allTouches[0].absoluteY;
        });

    const updateScale = useCallback(
        (scale) => {
            scaleRef.value = scale;
        },
        [scaleRef],
    );

    return (
        <View
            collapsable={false}
            style={[styles.flex1]}
        >
            <GestureDetector gesture={Pan}>
                <Animated.View
                    collapsable={false}
                    style={[StyleSheet.absoluteFill, styles.justifyContentCenter, styles.alignItemsCenter]}
                >
                    <BaseAttachmentViewPdf
                        // eslint-disable-next-line react/jsx-props-no-spreading
                        {...props}
                        updateScale={updateScale}
                    />
                </Animated.View>
            </GestureDetector>
        </View>
    );
}

AttachmentViewPdf.propTypes = attachmentViewPdfPropTypes;
AttachmentViewPdf.defaultProps = attachmentViewPdfDefaultProps;

export default memo(AttachmentViewPdf);
