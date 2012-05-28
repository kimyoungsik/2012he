//
//  PADtextViewController.h
//  iPhoneDEAF
//
//  Created by developer on 12. 1. 10..
//  Copyright (c) 2012 HeadFlow All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PADtextViewController : UIViewController
@property (retain, nonatomic) IBOutlet UITextView *padShowView;

@property(nonatomic, strong) NSString *padipsend;
@property(nonatomic, strong) NSString *padportsend;
@property(nonatomic, strong) NSString *tempstring;
- (IBAction)changeButtonPressed:(id)sender;


@end
