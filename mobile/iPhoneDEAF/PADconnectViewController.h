//
//  PADconnectViewController.h
//  iPhoneDEAF
//
//  Created by developer on 12. 1. 10..
//  Copyright (c) 2012 HeadFlow All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PADconnectViewController : UIViewController
@property (retain, nonatomic) IBOutlet UITextField *padIpAddress;
@property (retain, nonatomic) IBOutlet UITextField *padPortNumber;



- (IBAction)textFieldDoneEditing:(id)sender;
- (IBAction)backgroundTap:(id)sender;
@end



